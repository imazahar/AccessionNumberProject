package com.auddin.accessionnum;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestList {
	@Test
	public void getOrderedAccessionList1() {
		String slist = "AA12AA,A00100,A00099,A0001,ERR000111,ERR000112,"
				+ "ERR000113,ERR000115,ERR000116,ERR100114,ERR200000001,ERR200000002,"
				+ "ERR200000003,DRR2110012,SRR211001,ABCDEFG1";
		String str = AccessionNumberProcessor.getSortedList(slist);
		assertEquals("A0001,A00099-A00100,ABCDEFG1,DRR2110012,"
				+ "ERR000111-ERR000113,ERR000115-ERR000116,ERR100114," + "ERR200000001-ERR200000003,SRR211001",
				str);
	}

	@Test
	public void getOrderedAccessionList2() {
		String slist = "A00000,A0001,ERR000111,ERR000112,ERR000113,"
				+ "ERR000115,ERR000116,ERR100114,ERR200000001,ERR200000002,"
				+ "ERR200000003,DRR2110012,SRR211001,ABCDEFG1";
		assertEquals(
				"A00000,A0001,ABCDEFG1,DRR2110012,ERR000111-ERR000113,"
						+ "ERR000115-ERR000116,ERR100114,ERR200000001-ERR200000003,SRR211001",
				AccessionNumberProcessor.getSortedList(slist));
	}

	@Test
	public void getOrderedAccessionList3() {
		String slist = "A00000,A0001,ERR000111,ERR000112,ERR000113,"
				+ "ERR000115,ERR000116,ERR100114,ERR200000001,ERR200000002,"
				+ "ERR200000003,DRR2110012,}}2,}}1";
		assertEquals(
				"A00000,A0001,DRR2110012,ERR000111-ERR000113,"
						+ "ERR000115-ERR000116,ERR100114,ERR200000001-ERR200000003,}}1-}}2",
				AccessionNumberProcessor.getSortedList(slist));
	}

	@Test
	public void getOrderedAccessionList4() {
		String slist = "AABCDEFG1";
		assertEquals("AABCDEFG1",AccessionNumberProcessor.getSortedList(slist));
	}

	@Test
	public void getOrderedAccessionList5() {
		String slist = "AABCDEFG1,}}100010,}}100009";
		assertEquals("AABCDEFG1,}}100009-}}100010",AccessionNumberProcessor.getSortedList(slist));
	}

	@Test
	public void getOrderedAccessionList6() {
		String slist = "###!!!100010,###!!!100009,###!!!100015";
		String str = AccessionNumberProcessor.getSortedList(slist);
		assertEquals("###!!!100009-###!!!100010,###!!!100015",str);
	}
	
	@Test
	public void getOrderedAccessionList7() {
		String slist = "###ABC100016,###ABC100020,###ABC100019";
		String str = AccessionNumberProcessor.getSortedList(slist);
		assertEquals("###ABC100016,###ABC100019-###ABC100020",str);
	}
	
	@Test
	public void getOrderedAccessionList8() {
		String slist = "###ABC100016,###ABC100020,###ABC100018";
		String str = AccessionNumberProcessor.getSortedList(slist);
		assertEquals("###ABC100016,###ABC100018,###ABC100020",str);
	}

	@Test
	public void getOrderedAccessionListNeg() {
		String slist = "";
		String str = AccessionNumberProcessor.getSortedList(slist);
		assertEquals("",str);
	}

	@Test
	public void getOrderedAccessionListNull() {
		assertEquals(null,AccessionNumberProcessor.getSortedList(null));
	}
}
