//
//  ========================================================================
//  Copyright (c) 1995-2018 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.http2;

import java.util.Comparator;

import org.eclipse.jetty.util.ArrayTrie;
import org.eclipse.jetty.util.Trie;

public class HTTP2Cipher
{
    public static final Comparator<String> COMPARATOR = new CipherComparator();

    private final static Trie<Boolean> __blackProtocols = new ArrayTrie<>(6*5);
    private final static Trie<Boolean> __blackCiphers = new ArrayTrie<>(275*40);

    static
    {
        for (String p : new String[]
        {
                "TLSv1.2","TLSv1.1", "TLSv1", "SSL", "SSLv2", "SSLv3"
        })
        {
            __blackProtocols.put(p,Boolean.TRUE);
        }

        for (String c : new String[]
        {
            "TLS_NULL_WITH_NULL_NULL",
            "TLS_RSA_WITH_NULL_MD5",
            "TLS_RSA_WITH_NULL_SHA",
            "TLS_RSA_EXPORT_WITH_RC4_40_MD5",
            "TLS_RSA_WITH_RC4_128_MD5",
            "TLS_RSA_WITH_RC4_128_SHA",
            "TLS_RSA_EXPORT_WITH_RC2_CBC_40_MD5",
            "TLS_RSA_WITH_IDEA_CBC_SHA",
            "TLS_RSA_EXPORT_WITH_DES40_CBC_SHA",
            "TLS_RSA_WITH_DES_CBC_SHA",
            "TLS_RSA_WITH_3DES_EDE_CBC_SHA",
            "TLS_DH_DSS_EXPORT_WITH_DES40_CBC_SHA",
            "TLS_DH_DSS_WITH_DES_CBC_SHA",
            "TLS_DH_DSS_WITH_3DES_EDE_CBC_SHA",
            "TLS_DH_RSA_EXPORT_WITH_DES40_CBC_SHA",
            "TLS_DH_RSA_WITH_DES_CBC_SHA",
            "TLS_DH_RSA_WITH_3DES_EDE_CBC_SHA",
            "TLS_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA",
            "TLS_DHE_DSS_WITH_DES_CBC_SHA",
            "TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA",
            "TLS_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA",
            "TLS_DHE_RSA_WITH_DES_CBC_SHA",
            "TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA",
            "TLS_DH_anon_EXPORT_WITH_RC4_40_MD5",
            "TLS_DH_anon_WITH_RC4_128_MD5",
            "TLS_DH_anon_EXPORT_WITH_DES40_CBC_SHA",
            "TLS_DH_anon_WITH_DES_CBC_SHA",
            "TLS_DH_anon_WITH_3DES_EDE_CBC_SHA",
            "TLS_KRB5_WITH_DES_CBC_SHA",
            "TLS_KRB5_WITH_3DES_EDE_CBC_SHA",
            "TLS_KRB5_WITH_RC4_128_SHA",
            "TLS_KRB5_WITH_IDEA_CBC_SHA",
            "TLS_KRB5_WITH_DES_CBC_MD5",
            "TLS_KRB5_WITH_3DES_EDE_CBC_MD5",
            "TLS_KRB5_WITH_RC4_128_MD5",
            "TLS_KRB5_WITH_IDEA_CBC_MD5",
            "TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA",
            "TLS_KRB5_EXPORT_WITH_RC2_CBC_40_SHA",
            "TLS_KRB5_EXPORT_WITH_RC4_40_SHA",
            "TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5",
            "TLS_KRB5_EXPORT_WITH_RC2_CBC_40_MD5",
            "TLS_KRB5_EXPORT_WITH_RC4_40_MD5",
            "TLS_PSK_WITH_NULL_SHA",
            "TLS_DHE_PSK_WITH_NULL_SHA",
            "TLS_RSA_PSK_WITH_NULL_SHA",
            "TLS_RSA_WITH_AES_128_CBC_SHA",
            "TLS_DH_DSS_WITH_AES_128_CBC_SHA",
            "TLS_DH_RSA_WITH_AES_128_CBC_SHA",
            "TLS_DHE_DSS_WITH_AES_128_CBC_SHA",
            "TLS_DHE_RSA_WITH_AES_128_CBC_SHA",
            "TLS_DH_anon_WITH_AES_128_CBC_SHA",
            "TLS_RSA_WITH_AES_256_CBC_SHA",
            "TLS_DH_DSS_WITH_AES_256_CBC_SHA",
            "TLS_DH_RSA_WITH_AES_256_CBC_SHA",
            "TLS_DHE_DSS_WITH_AES_256_CBC_SHA",
            "TLS_DHE_RSA_WITH_AES_256_CBC_SHA",
            "TLS_DH_anon_WITH_AES_256_CBC_SHA",
            "TLS_RSA_WITH_NULL_SHA256",
            "TLS_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_RSA_WITH_AES_256_CBC_SHA256",
            "TLS_DH_DSS_WITH_AES_128_CBC_SHA256",
            "TLS_DH_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_DHE_DSS_WITH_AES_128_CBC_SHA256",
            "TLS_RSA_WITH_CAMELLIA_128_CBC_SHA",
            "TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA",
            "TLS_DH_RSA_WITH_CAMELLIA_128_CBC_SHA",
            "TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA",
            "TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA",
            "TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA",
            "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_DH_DSS_WITH_AES_256_CBC_SHA256",
            "TLS_DH_RSA_WITH_AES_256_CBC_SHA256",
            "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256",
            "TLS_DHE_RSA_WITH_AES_256_CBC_SHA256",
            "TLS_DH_anon_WITH_AES_128_CBC_SHA256",
            "TLS_DH_anon_WITH_AES_256_CBC_SHA256",
            "TLS_RSA_WITH_CAMELLIA_256_CBC_SHA",
            "TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA",
            "TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA",
            "TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA",
            "TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA",
            "TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA",
            "TLS_PSK_WITH_RC4_128_SHA",
            "TLS_PSK_WITH_3DES_EDE_CBC_SHA",
            "TLS_PSK_WITH_AES_128_CBC_SHA",
            "TLS_PSK_WITH_AES_256_CBC_SHA",
            "TLS_DHE_PSK_WITH_RC4_128_SHA",
            "TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA",
            "TLS_DHE_PSK_WITH_AES_128_CBC_SHA",
            "TLS_DHE_PSK_WITH_AES_256_CBC_SHA",
            "TLS_RSA_PSK_WITH_RC4_128_SHA",
            "TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA",
            "TLS_RSA_PSK_WITH_AES_128_CBC_SHA",
            "TLS_RSA_PSK_WITH_AES_256_CBC_SHA",
            "TLS_RSA_WITH_SEED_CBC_SHA",
            "TLS_DH_DSS_WITH_SEED_CBC_SHA",
            "TLS_DH_RSA_WITH_SEED_CBC_SHA",
            "TLS_DHE_DSS_WITH_SEED_CBC_SHA",
            "TLS_DHE_RSA_WITH_SEED_CBC_SHA",
            "TLS_DH_anon_WITH_SEED_CBC_SHA",
            "TLS_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_DH_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_DH_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_DH_DSS_WITH_AES_128_GCM_SHA256",
            "TLS_DH_DSS_WITH_AES_256_GCM_SHA384",
            "TLS_DH_anon_WITH_AES_128_GCM_SHA256",
            "TLS_DH_anon_WITH_AES_256_GCM_SHA384",
            "TLS_PSK_WITH_AES_128_GCM_SHA256",
            "TLS_PSK_WITH_AES_256_GCM_SHA384",
            "TLS_RSA_PSK_WITH_AES_128_GCM_SHA256",
            "TLS_RSA_PSK_WITH_AES_256_GCM_SHA384",
            "TLS_PSK_WITH_AES_128_CBC_SHA256",
            "TLS_PSK_WITH_AES_256_CBC_SHA384",
            "TLS_PSK_WITH_NULL_SHA256",
            "TLS_PSK_WITH_NULL_SHA384",
            "TLS_DHE_PSK_WITH_AES_128_CBC_SHA256",
            "TLS_DHE_PSK_WITH_AES_256_CBC_SHA384",
            "TLS_DHE_PSK_WITH_NULL_SHA256",
            "TLS_DHE_PSK_WITH_NULL_SHA384",
            "TLS_RSA_PSK_WITH_AES_128_CBC_SHA256",
            "TLS_RSA_PSK_WITH_AES_256_CBC_SHA384",
            "TLS_RSA_PSK_WITH_NULL_SHA256",
            "TLS_RSA_PSK_WITH_NULL_SHA384",
            "TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256",
            "TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256",
            "TLS_DH_RSA_WITH_CAMELLIA_128_CBC_SHA256",
            "TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256",
            "TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256",
            "TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256",
            "TLS_RSA_WITH_CAMELLIA_256_CBC_SHA256",
            "TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256",
            "TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256",
            "TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256",
            "TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256",
            "TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256",
            "TLS_EMPTY_RENEGOTIATION_INFO_SCSV",
            "TLS_ECDH_ECDSA_WITH_NULL_SHA",
            "TLS_ECDH_ECDSA_WITH_RC4_128_SHA",
            "TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA",
            "TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_NULL_SHA",
            "TLS_ECDHE_ECDSA_WITH_RC4_128_SHA",
            "TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDH_RSA_WITH_NULL_SHA",
            "TLS_ECDH_RSA_WITH_RC4_128_SHA",
            "TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA",
            "TLS_ECDH_RSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDH_RSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_NULL_SHA",
            "TLS_ECDHE_RSA_WITH_RC4_128_SHA",
            "TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDH_anon_WITH_NULL_SHA",
            "TLS_ECDH_anon_WITH_RC4_128_SHA",
            "TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA",
            "TLS_ECDH_anon_WITH_AES_128_CBC_SHA",
            "TLS_ECDH_anon_WITH_AES_256_CBC_SHA",
            "TLS_SRP_SHA_WITH_3DES_EDE_CBC_SHA",
            "TLS_SRP_SHA_RSA_WITH_3DES_EDE_CBC_SHA",
            "TLS_SRP_SHA_DSS_WITH_3DES_EDE_CBC_SHA",
            "TLS_SRP_SHA_WITH_AES_128_CBC_SHA",
            "TLS_SRP_SHA_RSA_WITH_AES_128_CBC_SHA",
            "TLS_SRP_SHA_DSS_WITH_AES_128_CBC_SHA",
            "TLS_SRP_SHA_WITH_AES_256_CBC_SHA",
            "TLS_SRP_SHA_RSA_WITH_AES_256_CBC_SHA",
            "TLS_SRP_SHA_DSS_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384",
            "TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384",
            "TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384",
            "TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_PSK_WITH_RC4_128_SHA",
            "TLS_ECDHE_PSK_WITH_3DES_EDE_CBC_SHA",
            "TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA256",
            "TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA384",
            "TLS_ECDHE_PSK_WITH_NULL_SHA",
            "TLS_ECDHE_PSK_WITH_NULL_SHA256",
            "TLS_ECDHE_PSK_WITH_NULL_SHA384",
            "TLS_RSA_WITH_ARIA_128_CBC_SHA256",
            "TLS_RSA_WITH_ARIA_256_CBC_SHA384",
            "TLS_DH_DSS_WITH_ARIA_128_CBC_SHA256",
            "TLS_DH_DSS_WITH_ARIA_256_CBC_SHA384",
            "TLS_DH_RSA_WITH_ARIA_128_CBC_SHA256",
            "TLS_DH_RSA_WITH_ARIA_256_CBC_SHA384",
            "TLS_DHE_DSS_WITH_ARIA_128_CBC_SHA256",
            "TLS_DHE_DSS_WITH_ARIA_256_CBC_SHA384",
            "TLS_DHE_RSA_WITH_ARIA_128_CBC_SHA256",
            "TLS_DHE_RSA_WITH_ARIA_256_CBC_SHA384",
            "TLS_DH_anon_WITH_ARIA_128_CBC_SHA256",
            "TLS_DH_anon_WITH_ARIA_256_CBC_SHA384",
            "TLS_ECDHE_ECDSA_WITH_ARIA_128_CBC_SHA256",
            "TLS_ECDHE_ECDSA_WITH_ARIA_256_CBC_SHA384",
            "TLS_ECDH_ECDSA_WITH_ARIA_128_CBC_SHA256",
            "TLS_ECDH_ECDSA_WITH_ARIA_256_CBC_SHA384",
            "TLS_ECDHE_RSA_WITH_ARIA_128_CBC_SHA256",
            "TLS_ECDHE_RSA_WITH_ARIA_256_CBC_SHA384",
            "TLS_ECDH_RSA_WITH_ARIA_128_CBC_SHA256",
            "TLS_ECDH_RSA_WITH_ARIA_256_CBC_SHA384",
            "TLS_RSA_WITH_ARIA_128_GCM_SHA256",
            "TLS_RSA_WITH_ARIA_256_GCM_SHA384",
            "TLS_DH_RSA_WITH_ARIA_128_GCM_SHA256",
            "TLS_DH_RSA_WITH_ARIA_256_GCM_SHA384",
            "TLS_DH_DSS_WITH_ARIA_128_GCM_SHA256",
            "TLS_DH_DSS_WITH_ARIA_256_GCM_SHA384",
            "TLS_DH_anon_WITH_ARIA_128_GCM_SHA256",
            "TLS_DH_anon_WITH_ARIA_256_GCM_SHA384",
            "TLS_ECDH_ECDSA_WITH_ARIA_128_GCM_SHA256",
            "TLS_ECDH_ECDSA_WITH_ARIA_256_GCM_SHA384",
            "TLS_ECDH_RSA_WITH_ARIA_128_GCM_SHA256",
            "TLS_ECDH_RSA_WITH_ARIA_256_GCM_SHA384",
            "TLS_PSK_WITH_ARIA_128_CBC_SHA256",
            "TLS_PSK_WITH_ARIA_256_CBC_SHA384",
            "TLS_DHE_PSK_WITH_ARIA_128_CBC_SHA256",
            "TLS_DHE_PSK_WITH_ARIA_256_CBC_SHA384",
            "TLS_RSA_PSK_WITH_ARIA_128_CBC_SHA256",
            "TLS_RSA_PSK_WITH_ARIA_256_CBC_SHA384",
            "TLS_PSK_WITH_ARIA_128_GCM_SHA256",
            "TLS_PSK_WITH_ARIA_256_GCM_SHA384",
            "TLS_RSA_PSK_WITH_ARIA_128_GCM_SHA256",
            "TLS_RSA_PSK_WITH_ARIA_256_GCM_SHA384",
            "TLS_ECDHE_PSK_WITH_ARIA_128_CBC_SHA256",
            "TLS_ECDHE_PSK_WITH_ARIA_256_CBC_SHA384",
            "TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_CBC_SHA256",
            "TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_CBC_SHA384",
            "TLS_ECDH_ECDSA_WITH_CAMELLIA_128_CBC_SHA256",
            "TLS_ECDH_ECDSA_WITH_CAMELLIA_256_CBC_SHA384",
            "TLS_ECDHE_RSA_WITH_CAMELLIA_128_CBC_SHA256",
            "TLS_ECDHE_RSA_WITH_CAMELLIA_256_CBC_SHA384",
            "TLS_ECDH_RSA_WITH_CAMELLIA_128_CBC_SHA256",
            "TLS_ECDH_RSA_WITH_CAMELLIA_256_CBC_SHA384",
            "TLS_RSA_WITH_CAMELLIA_128_GCM_SHA256",
            "TLS_RSA_WITH_CAMELLIA_256_GCM_SHA384",
            "TLS_DH_RSA_WITH_CAMELLIA_128_GCM_SHA256",
            "TLS_DH_RSA_WITH_CAMELLIA_256_GCM_SHA384",
            "TLS_DH_DSS_WITH_CAMELLIA_128_GCM_SHA256",
            "TLS_DH_DSS_WITH_CAMELLIA_256_GCM_SHA384",
            "TLS_DH_anon_WITH_CAMELLIA_128_GCM_SHA256",
            "TLS_DH_anon_WITH_CAMELLIA_256_GCM_SHA384",
            "TLS_ECDH_ECDSA_WITH_CAMELLIA_128_GCM_SHA256",
            "TLS_ECDH_ECDSA_WITH_CAMELLIA_256_GCM_SHA384",
            "TLS_ECDH_RSA_WITH_CAMELLIA_128_GCM_SHA256",
            "TLS_ECDH_RSA_WITH_CAMELLIA_256_GCM_SHA384",
            "TLS_PSK_WITH_CAMELLIA_128_GCM_SHA256",
            "TLS_PSK_WITH_CAMELLIA_256_GCM_SHA384",
            "TLS_RSA_PSK_WITH_CAMELLIA_128_GCM_SHA256",
            "TLS_RSA_PSK_WITH_CAMELLIA_256_GCM_SHA384",
            "TLS_PSK_WITH_CAMELLIA_128_CBC_SHA256",
            "TLS_PSK_WITH_CAMELLIA_256_CBC_SHA384",
            "TLS_DHE_PSK_WITH_CAMELLIA_128_CBC_SHA256",
            "TLS_DHE_PSK_WITH_CAMELLIA_256_CBC_SHA384",
            "TLS_RSA_PSK_WITH_CAMELLIA_128_CBC_SHA256",
            "TLS_RSA_PSK_WITH_CAMELLIA_256_CBC_SHA384",
            "TLS_ECDHE_PSK_WITH_CAMELLIA_128_CBC_SHA256",
            "TLS_ECDHE_PSK_WITH_CAMELLIA_256_CBC_SHA384",
            "TLS_RSA_WITH_AES_128_CCM",
            "TLS_RSA_WITH_AES_256_CCM",
            "TLS_RSA_WITH_AES_128_CCM_8",
            "TLS_RSA_WITH_AES_256_CCM_8",
            "TLS_PSK_WITH_AES_128_CCM",
            "TLS_PSK_WITH_AES_256_CCM",
            "TLS_PSK_WITH_AES_128_CCM_8",
            "TLS_PSK_WITH_AES_256_CCM_8"
        })
        {
            __blackCiphers.put(c,Boolean.TRUE);
        }
    }

    public static boolean isBlackListProtocol(String tlsProtocol)
    {
        Boolean b = __blackProtocols.get(tlsProtocol);
        return b != null && b;
    }

    public static boolean isBlackListCipher(String tlsCipher)
    {
        Boolean b = __blackCiphers.get(tlsCipher);
        return b != null && b;
    }

    /**
     * Comparator that orders non blacklisted ciphers before blacklisted ones.
     */
    public static class CipherComparator implements Comparator<String>
    {
        @Override
        public int compare(String c1, String c2)
        {
            boolean b1=isBlackListCipher(c1);
            boolean b2=isBlackListCipher(c2);
            if (b1==b2)
                return 0;
            if (b1)
                return 1;
            return -1;
        }
    }
}
