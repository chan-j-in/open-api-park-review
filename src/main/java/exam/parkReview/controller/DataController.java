package exam.parkReview.controller;

import exam.parkReview.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/data")
public class DataController {

    private final DataService dataService;

    @PostMapping("/save")
    public ResponseEntity<String> save() {

        dataService.save();
        return ResponseEntity.ok().body("데이터 불러오기 완료");
    }
}
