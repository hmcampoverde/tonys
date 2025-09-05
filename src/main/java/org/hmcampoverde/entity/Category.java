package org.hmcampoverde.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_category")
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Category extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id", nullable = false, length = 2)
	private Long id;

	@Column(name = "category_name", unique = true, nullable = false, length = 75)
	private String name;

	@Column(name = "category_visible", nullable = false, columnDefinition = "BOOLEAN DEFAULT 'TRUE'")
	private boolean visible;

	@Column(name = "category_icon", nullable = false, length = 75)
	private String icon;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", nullable = true, foreignKey = @ForeignKey(name = "FK_CATEGORY_PARENT"))
	private Category parent;
}
