package com.becoder.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private String secretey = "";

	public String generateToken(String username) {

		Map<String, Object> claims = new HashMap<>();
		claims.put("username", username);

		String token = Jwts.builder().claims().add(claims).subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 60 * 10)).and().signWith(getKey()).compact();

		return token;
	}

	private Key getKey() {

		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretey = Base64.getEncoder().encodeToString(sk.getEncoded());

		} catch (Exception e) {
			e.printStackTrace();
		}

		byte[] keyBytes = Decoders.BASE64.decode(secretey);
		return Keys.hmacShaKeyFor(keyBytes);

	}

}
