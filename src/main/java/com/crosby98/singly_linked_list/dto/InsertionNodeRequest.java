package com.crosby98.singly_linked_list.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class InsertionNodeRequest {
    private Object data;
    private Object after;
}