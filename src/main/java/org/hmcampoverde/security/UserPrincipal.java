package org.hmcampoverde.security;

import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserPrincipal extends User {

	private org.hmcampoverde.entity.User user;

	public UserPrincipal(org.hmcampoverde.entity.User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), user.isActive(), true, true, true, authorities);
		this.user = user;
	}
}
