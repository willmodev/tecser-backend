package com.tecser.backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;


@Data
public class SaleRequestDTO {
    @NotBlank(message = "El número de venta es obligatorio")
    @Pattern(regexp = "^[A-Z0-9]{4,10}$", message = "El número de venta debe tener entre 4 y 10 caracteres alfanuméricos en mayúsculas")
    private String saleNumber;

    @NotNull(message = "El ID del vendedor es obligatorio")
    @Positive(message = "El ID del vendedor debe ser un número positivo")
    private Long sellerId;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto total debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "El monto total debe tener máximo 8 dígitos enteros y 2 decimales")
    private BigDecimal totalAmount;

    @Size(max = 500, message = "Los comentarios no pueden exceder los 500 caracteres")
    private String comments;

    @NotEmpty(message = "Debe incluir al menos un detalle de venta")
    private List<SaleDetailRequestDTO> saleDetails;
}