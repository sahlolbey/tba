package com.tba.tbamsg;

import com.tba.common.util.ArithmeticUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TbaMsgApplicationTests {

	@Test
	public void contextLoads() {
	}
	@Test
	public void testArithmeticUtil(){
	 Assert.assertEquals(422,
			Math.round(ArithmeticUtil.calculateXcoordinate(5, 10, 60, 300)));

	 Assert.assertEquals(727,
			 Math.round(ArithmeticUtil.calculateYcoordinate(5, 10,60,300)));
	}


}
