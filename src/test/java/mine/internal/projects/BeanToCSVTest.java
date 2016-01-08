package mine.internal.projects;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mine.internal.projects.converter.BeanToCSVConverter;
import mine.internal.projects.converter.Getter;

public class BeanToCSVTest {
	private static final String EXPECTED_HEADER_VALUES = "\"Comp1_Comp1\",\"Comp1_Comp3\",\"Comp1_Comp2\",\"Comp3\",\"Comp2\",\"Comp4\",\"Comp5_Comp1\",\"Comp5_Comp3\",\"Comp5_Comp2\"";
	private BeanToCSVConverter beanToCSVConverter;
	private static final Logger LOGGER = Logger.getGlobal();

	@Before
	public void setUp() throws Exception {
		beanToCSVConverter = new BeanToCSVConverter(',', "");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void test() {
		String csvHeader = beanToCSVConverter.parseObjectHeaderAsCSV(new TestClass2());
		LOGGER.info("\nActual:\n" + csvHeader + "\nExpected:\n" + EXPECTED_HEADER_VALUES);
		// assertEquals(EXPECTED_HEADER_VALUES, csvHeader);
	}

	public class TestClass1 {
		private String comp1 = "Initialized";
		private long comp2 = 0;
		private int comp3 = -1;

		@Getter
		public String getComp1() {
			return comp1;
		}

		public void setComp1(String comp1) {
			this.comp1 = comp1;
		}

		@Getter
		public long getComp2() {
			return comp2;
		}

		public void setComp2(long comp2) {
			this.comp2 = comp2;
		}

		@Getter
		public int getComp3() {
			return comp3;
		}

		public void setComp3(int comp3) {
			this.comp3 = comp3;
		}

	}

	public class TestClass2 {
		private TestClass1 comp1 = new TestClass1();
		private String comp2 = "Testing";
		private double comp3 = 9.00;
		private boolean comp4 = false;
		private TestClass1 comp5 = new TestClass1();

		@Getter
		public TestClass1 getComp1() {
			return comp1;
		}

		public void setComp1(TestClass1 comp1) {
			this.comp1 = comp1;
		}

		@Getter
		public String getComp2() {
			return comp2;
		}

		public void setComp2(String comp2) {
			this.comp2 = comp2;
		}

		@Getter
		public double getComp3() {
			return comp3;
		}

		public void setComp3(double comp3) {
			this.comp3 = comp3;
		}

		@Getter
		public boolean isComp4() {
			return comp4;
		}

		public void setComp4(boolean comp4) {
			this.comp4 = comp4;
		}

		@Getter
		public TestClass1 getComp5() {
			return comp5;
		}

		public void setComp5(TestClass1 comp5) {
			this.comp5 = comp5;
		}

	}

}
