package com.example.authtest.context;

import com.example.authtest.dto.ChzzkToken;
import lombok.Getter;
import lombok.Setter;

public class ServerContext {
    @Setter
    @Getter
    private static ChzzkToken chzzkToken;

}
