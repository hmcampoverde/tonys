package org.hmcampoverde.security;

import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserPrincipal extends User {

	private org.hmcampoverde.model.User user;

	public UserPrincipal(org.hmcampoverde.model.User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), user.isActived(), true, true, true, authorities);
		this.user = user;
	}
}
