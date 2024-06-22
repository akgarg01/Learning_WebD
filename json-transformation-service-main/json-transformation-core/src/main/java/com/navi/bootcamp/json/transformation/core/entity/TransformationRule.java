package com.navi.bootcamp.json.transformation.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.navi.bootcamp.json.transformation.core.contract.RuleTemplate;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@Table(name = "transformation_rules")
@JsonIgnoreProperties({"createdAt", "updatedAt"})
@TypeDef(name = "json", typeClass = JsonStringType.class)
@NoArgsConstructor
@AllArgsConstructor
public class TransformationRule extends BaseEntity {

    @NotNull
    private String ruleName;

    @NotNull
    private String ruleOwner;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private Integer ruleVersion;

    @NotNull
    private String uploadedBy;

    @NotNull
    private String lastUpdatedBy;

    @Type(type = "json")
    private List<RuleTemplate> ruleSpecs;

    private String comment;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        TransformationRule that = (TransformationRule) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
