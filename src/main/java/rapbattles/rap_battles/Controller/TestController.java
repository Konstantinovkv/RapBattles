package rapbattles.rap_battles.Controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    //test
    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Test json(){
        return new Test("Testing",", testing 1,2,3.");
    }

    @Setter
    @Getter
    @AllArgsConstructor
    private class Test {

        private String text;
        private String text2;
    }
}
