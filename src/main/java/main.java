public class main {

    public static void main(final String[] a) throws Exception {
        byte[] p = new byte[144];
        byte[] d = new byte[144];
        int[] l = null;
        int m = 0;
        int u;

        while ((u = System.in.read ()) != -1) {
            if (u == 88) {
                d[m] = 1;
                m++;
            } else if (u == 79) {
                m++;
            }
        }

        m = 145;
        for(u = 0; u < d.length; u ++) {
            if(d[u] == 1) {
                if (p[u] == 0) {
                    int[] r = new int[144];
                    c(u, r, -1, p, d);
                    int q = 0;
                    while(r[q] != 0) {
                        q++;
                    }

                    if(q < m) {
                        m = q;
                        l = r;
                    }
                }
            }
        }

        if(m != 145) {
            String o = "";
            for(u = 0; u < l.length; u++) {
                if(l[u]==0) {
                    break;
                }
                o = o + "(" + (l[u]-1) % 12 + ", " + (l[u]-1) / 12 + ")";
                if(u+1 < l.length && l[u+1] != 0) {
                    o = o+ ", ";
                }
            }
            System.out.println(m + " => " + o);
        }
    }

    private static int[] c(int e, int[] f, int g, byte[]p, byte[] d) {
        p[e] = 1;
        f[++g] = e + 1;
        int[] q = new int[8];
        int j = 0;

        if(e % 12 != 0 && e > 11 && d[e-13] == 1) {
            q[j++] = e - 13;
        }
        if(e > 11 && d[e - 12] == 1) {
            q[j++] = e - 12;
        }
        if(e % 12 != 11 && e > 11 && d[e - 11] == 1) {
            q[j++] = e - 11;
        }
        if(e % 12 != 11 && d[e + 1] == 1) {
            q[j++] = e + 1;
        }
        if(e % 12 != 11 && e < 132 && d[e + 13] == 1) {
            q[j++] = e + 13;
        }
        if(e < 132 && d[e + 12] == 1) {
            q[j++] = e + 12;
        }
        if(e % 12 != 0 && e < 132 && d[e + 11] == 1) {
            q[j++] = e + 11;
        }
        if(e % 12 != 0 && d[e - 1] == 1) {
            q[j] = e - 1;
        }

        for(int i = 0; i < j; i++) {
            if(p[q[i]] == 0) {
                c(q[i], f, g, p, d);
            }
        }

        return f;
    }
}
