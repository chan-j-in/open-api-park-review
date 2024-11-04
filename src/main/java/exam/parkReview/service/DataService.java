package exam.parkReview.service;

import exam.parkReview.domain.entity.Park;
import exam.parkReview.exception.AppException;
import exam.parkReview.exception.ErrorCode;
import exam.parkReview.repository.ParkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DataService {

    private final ParkRepository parkRepository;

    @Value("${api.base-url}")
    private String baseUrl;

    @Value("${api.key}")
    private String key;

    @Value("${api.type}")
    private String type;

    @Value("${api.service}")
    private String service;

    @Transactional
    public ResponseEntity<String> save() {

        String result = "";

        try {
            URL url = new URL(baseUrl
                    + "/" + key
                    + "/" + type
                    + "/" + service
                    + "/1/1"
            );

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = br.readLine();

            if (result == null || result.isEmpty()) {
                throw new AppException(ErrorCode.DATA_LOAD_FAILED, "API 호출 결과가 비어있습니다.");
            }

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject parkStats = (JSONObject) jsonObject.get(service);

            if (parkStats == null) {
                throw new AppException(ErrorCode.DATA_LOAD_FAILED, "API 응답에서 parkStats를 찾을 수 없습니다.");
            }

            int totalCount = ((Long) parkStats.get("list_total_count")).intValue();
            int pageSize = 10;
            int totalPages = (int) Math.ceil((double) totalCount / pageSize);

            for (int page = 1; page <= totalPages; page++) {
                int startIndex = (page - 1) * pageSize + 1;
                int endIndex = Math.min(page * pageSize, totalCount);

                url = new URL(baseUrl
                        + "/" + key
                        + "/" + type
                        + "/" + service
                        + "/" + startIndex
                        + "/" + endIndex
                );

                br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                result = br.readLine();

                if (result == null || result.isEmpty()) {
                    throw new AppException(ErrorCode.DATA_LOAD_FAILED, "API 호출 결과가 비어있습니다.");
                }

                jsonObject = (JSONObject) jsonParser.parse(result);
                parkStats = (JSONObject) jsonObject.get(service);

                if (parkStats == null) {
                    throw new AppException(ErrorCode.DATA_LOAD_FAILED, "API 응답에서 parkStats를 찾을 수 없습니다.");
                }

                JSONArray infoArr = (JSONArray) parkStats.get("row"); // 페이지별로 row 배열 가져오기

                if (infoArr == null) {
                    throw new AppException(ErrorCode.DATA_LOAD_FAILED, "API 응답에서 정보 배열을 찾을 수 없습니다.");
                }

                for (Object obj : infoArr) {
                    JSONObject tmp = (JSONObject) obj;
                    Park park = Park.builder()
                            .parkNum(Long.parseLong((String) tmp.get("P_IDX")))
                            .parkName((String)tmp.get("P_PARK"))
                            .parkSummary((String)tmp.get("P_LIST_CONTENT"))
                            .mainFacilities((String)tmp.get("MAIN_EQUIP"))
                            .guideMap((String)tmp.get("GUIDANCE"))
                            .address((String)tmp.get("P_ADDR"))
                            .websiteUrl((String)tmp.get("TEMPLATE_URL"))
                            .build();
                    parkRepository.save(park);
                }


            }


        } catch (Exception e) {
            throw new AppException(ErrorCode.DATA_LOAD_FAILED, "API 호출 중 오류가 발생했습니다: " + e.getMessage());
        }
        return ResponseEntity.ok().body("데이터 불러오기 완료");
    }

    @Scheduled(cron = "0 0 0 * * MON")
    @Transactional
    public void updateDataWeekly() {
        try {
            log.info("업데이트 시작");
            clearParks();
            save();
            log.info("업데이트 완료");
        } catch (Exception e) {
            throw new AppException(ErrorCode.DATA_LOAD_FAILED, "API 호출 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Transactional
    public void clearParks() {
        parkRepository.deleteAll();
    }
}
