package org.develcorp.services.account.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExceptionResponse {

    @ApiModelProperty (notes = "La URI que identifica la categoria del error.", name = "type",
            required = true, example = "/errors/authentication/not-authorized")
    private String type = "/errors/uncategorized";

    @ApiModelProperty(notes = "Un mensaje legible referente al error", name = "title",
            required = true, example = "El usuario no esta autorizado")
    private String title;

    @ApiModelProperty(notes = "El codigo unico de error", name = "code", required = false, example = "404")
    private String code;

    @ApiModelProperty(notes = "Explicacion legible del error", name = "detail",
            required = true, example = "El usuario no cuenta con los permisos necesarios para acceder a esta instancia.")
    private String detail;

    @ApiModelProperty(notes = "URI que identifica la ocurrencia especifica del error", name = "instancia",
            required = true, example = "/errors/authentication/not-authorized/01")
    private String instance;

    public ExceptionResponse(String title, String code, String detail) {
        super();
        this.title = title;
        this.code = code;
        this.detail = detail;
    }
}
