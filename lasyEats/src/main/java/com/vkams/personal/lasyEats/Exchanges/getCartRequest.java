package com.vkams.personal.lasyEats.Exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class getCartRequest {
    @NotNull private String userId;
    public String getUserId()
    {
        return this.userId;
    }
    public void setUserId(@RequestParam(value= "userId")String userId)
    {
        this.userId=userId;
    }
}
