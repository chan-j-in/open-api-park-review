package exam.parkReview.service;

import exam.parkReview.domain.entity.Park;
import exam.parkReview.repository.ParkRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject parkStats = (JSONObject) jsonObject.get(service);

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

                jsonObject = (JSONObject) jsonParser.parse(result);
                parkStats = (JSONObject) jsonObject.get(service);
                JSONArray infoArr = (JSONArray) parkStats.get("row"); // 페이지별로 row 배열 가져오기

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
            e.printStackTrace();
        }
        return ResponseEntity.ok().body("데이터 불러오기 완료");
    }
}
