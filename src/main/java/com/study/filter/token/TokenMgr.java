package com.study.filter.token;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.study.Constant;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@SuppressWarnings("restriction")
public class TokenMgr {

    /**
     * 创建SecretKey
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(Constant.JWT_SECRET);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 签发JWT
     *
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public static String createJWT(String id, String subject, String role, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        System.out.println("主题" + subject + "   " + role);
        SecretKey secretKey = generalKey();
//        JwtBuilder builder = Jwts.builder()
//                .setId(id)
//                .setSubject(subject)
//                .setIssuedAt(now)
//                .signWith(signatureAlgorithm, secretKey);
//        Claims claims = Jwts.claims();
//        claims.setSubject("测试主题~");
//        claims.put(key, value);	//设置其他属性，类似map放入键值对，再用get()取出即可
        JwtBuilder builder = Jwts.builder()
                .setId(id)                                        // JWT_ID
                .setSubject(subject)                            // 主题
                .setAudience(role)                                // 接受者
                //.setClaims(null)                                // 自定义属性
                .setIssuer("")                                  // 签发者
                .setNotBefore(new Date())                       // 开始时间
                .setIssuedAt(now)                                // 签发时间
                .signWith(signatureAlgorithm, secretKey);        // 签名算法以及密匙
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate);                        // 失效时间
        }
        return builder.compact();
    }

    /**
     * 验证JWT
     *
     * @param jwtStr
     * @return
     */
    public static CheckPOJO validateJWT(String jwtStr) {
        CheckPOJO checkResult = new CheckPOJO();
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setErrCode(Constant.JWT_ERRCODE_EXPIRE);
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            checkResult.setErrCode(Constant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrCode(Constant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    /**
     * 解析JWT字符串
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
