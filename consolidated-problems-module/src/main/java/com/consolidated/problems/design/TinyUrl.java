package com.consolidated.problems.design;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TinyUrl {
	public static void main(String[] args) {
		System.out.println("Approach4: Tiny Url: ");
		Codec3 ob4 = new Codec3();
		String longUrl = "https://leetcode.com/problems/design-tinyurl";

		String tinyUrl = ob4.encode(longUrl);
		System.out.println("Encoded Url: " + tinyUrl);
		System.out.println("Decoded Url: " + ob4.decode(tinyUrl));
	}
}

// Approach 1- Using simple counter
class Codec1 {
	Map<Integer, String> map = new HashMap<>();
	int i = 0;

	// Encodes a URL to a shortened URL.
	public String encode(String longUrl) {
		map.put(i, longUrl);
		return "http://tinyurl.com/" + i++;
	}

	// Decodes a shortened URL to its original URL.
	public String decode(String shortUrl) {
		int key = Integer.parseInt(shortUrl.replace("http://tinyurl.com/", ""));
		return map.get(key);
	}
}

// Approach 2- using hashcode
class Codec2 {
	Map<Integer, String> map = new HashMap<>();

	public String encode(String longUrl) {
		map.put(longUrl.hashCode(), longUrl);
		return "http://tinyurl.com/" + longUrl.hashCode();
	}

	public String decode(String shortUrl) {
		return map.get(Integer.parseInt(shortUrl.replace("http://tinyurl.com/", "")));
	}
}

// Approach 3- using Base62 conversion -> 6 digit short url
class Codec3 {
	private Map<String, String> longUrlMap = new HashMap<>();
	private Map<String, String> shortUrlMap = new HashMap<>();
	private static final String HOST = "http://tinyurl.com/";
	private static final String SEED = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public String encode(String longUrl) {
		if (longUrlMap.containsKey(longUrl))
			return HOST + longUrlMap.get(longUrl);

		String encodedUrl = base62Conversion();
		longUrlMap.put(longUrl, encodedUrl);
		shortUrlMap.put(encodedUrl, longUrl);

		return HOST + encodedUrl;
	}

	public String decode(String shortUrl) {
		//String encodedUrl = shortUrl.replace("http://tinyurl.com/", "");
		//return shortUrlMap.get(encodedUrl);
		//or
		return shortUrlMap.get(shortUrl.substring(HOST.length()));
	}

	private String base62Conversion() {
		StringBuilder sb = null;
		do {
			sb = new StringBuilder();
			for (int i = 0; i < 6; i++) {
				int random = (int) (Math.random() * SEED.length());
				System.out.println(random);
				sb.append(SEED.charAt(random));
			}
		} while (shortUrlMap.containsKey(sb.toString()));

		return sb.toString();
	}
}

//Approach4: using Base64 conversion 
class Codec4 {
	public String encode(String longUrl) {
		return Base64.getUrlEncoder().encodeToString(longUrl.getBytes(StandardCharsets.UTF_8));
	}

	public String decode(String shortUrl) {
		return new String(Base64.getUrlDecoder().decode(shortUrl));
	}
}