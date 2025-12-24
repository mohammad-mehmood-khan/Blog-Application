package org.mehmood.blogapplicationbackendproject.payLoads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomApiResponse {
   private String message;
    private Boolean success;
}
