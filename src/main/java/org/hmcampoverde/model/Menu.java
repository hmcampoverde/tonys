package org.hmcampoverde.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_menu")
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Menu extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id", nullable = false, length = 2)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "menu_name", nullable = false, unique = true, length = 75)
	private String name;

	@Column(name = "menu_url", nullable = true, columnDefinition = "TEXT")
	private String url;

	@Column(name = "menu_icon", nullable = true, length = 30)
	private String icon;

	@ManyToOne
	@JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "FK_MENU_SUBMENU"), nullable = true)
	private Menu parent;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "tbl_role_menu",
		joinColumns = @JoinColumn(name = "menu_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id"),
		foreignKey = @ForeignKey(name = "FK_MENU_ROLE"),
		inverseForeignKey = @ForeignKey(name = "FK_ROLE_MENU")
	)
	private Set<Role> roles;
}
