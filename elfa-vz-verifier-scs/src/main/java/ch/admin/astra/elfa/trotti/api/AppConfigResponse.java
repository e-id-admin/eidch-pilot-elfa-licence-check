package ch.admin.astra.elfa.trotti.api;

import jakarta.validation.constraints.NotNull;

public record AppConfigResponse(

    @NotNull
    String version,

    @NotNull
    String environment

){}
