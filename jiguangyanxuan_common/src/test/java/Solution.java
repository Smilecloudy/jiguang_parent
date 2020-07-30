import org.junit.Test;

/**
 * @auther: cyb
 * @create: 2020/7/18 15:50
 */
public class Solution {
    @Test
    public void test() throws Exception {

        int reverse = reverse(333333333);
        System.out.println(reverse);
    }

    public int reverse(int x) {

        int res = 0;
        while (x != 0) {
            int t = x % 10;
            int newRes = res * 10 + t;
            //如果数字溢出，直接返回0
            if ((newRes - t) / 10 != res)
                return 0;
            res = newRes;
            x = x / 10;
        }
        return res;
    }

}
