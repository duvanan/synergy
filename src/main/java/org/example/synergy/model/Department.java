/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.model;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department  implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 192178532347017224L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;
    
    @Column(name = "`code`", nullable = false, length = 20)
    private String code;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "region_code")
    private String regionCode;
    
    @Column(name = "region_name")
    private String regionName;
    
    @Column(name = "region_fullname")
    private String regionFullname;
    
    @Column(name = "description")
    private String description;
}
