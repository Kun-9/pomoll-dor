package kun.pomondor.web.exhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ErrorResult {

    private String code;
    private String message;
}
