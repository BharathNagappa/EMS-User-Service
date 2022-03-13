package com.ems;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ems.entity.User;
import com.ems.entity.Users;
import com.ems.service.UserService;
import com.ems.util.JWTTokenUtil;

@Component
public class EMSRequestFilter extends OncePerRequestFilter{

	@Autowired
	JWTTokenUtil jwtTokenUtil;
	
	@Autowired
	UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		/*if(SecurityContextHolder.getContext().getAuthentication()==null)
		{
			String bearerToken =  request.getHeader("Authorization");
			String userName = null;
			String token = null;
			if(bearerToken!=null && bearerToken.length()>0)
			{
				try
				{
					token = bearerToken.substring(bearerToken.indexOf(" "),bearerToken.length()).trim();
					System.out.println(token);
					userName = jwtTokenUtil.getUsernameFromToken(token);
				}
				catch(Exception e)
				{
					System.out.println("Invalid JWT");
				}

			}

			if(userName!=null && jwtTokenUtil.validateToken(token))
			{

				Iterable<User> users = userService.getUserByName(userName);
				Users myUser = new Users(users.iterator().next());
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(myUser.getUsername(),myUser.getPassword());
				//usernamePasswordAuthenticationToken
				//.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
		*/
		if(SecurityContextHolder.getContext().getAuthentication()==null)
		{
			String bearerToken =  request.getHeader("Authorization");
			if(bearerToken!=null && bearerToken.length()>0)
			{
				String token = bearerToken.substring(bearerToken.indexOf(" "),bearerToken.length()).trim();
				System.out.println(token);
				if(jwtTokenUtil.validateToken(token))
				{
					String userName = jwtTokenUtil.getUsernameFromToken(token);
					Iterable<User> users = userService.getUserByName(userName);
					Users myUser = new Users(users.iterator().next());
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(myUser, null, myUser.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}
		filterChain.doFilter(request, response);

	}

}
