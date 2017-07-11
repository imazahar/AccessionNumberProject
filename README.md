# Project Title

Return ordered list of accession numbers. A list of comma separated accession numbers as input is given.

An accession number has the following format: L...LN...N (e.g. AB1234)

where L...L denotes one or more ASCII7 letters and N denotes one or more digits (0,1,2,3,4,5,6,7,8 or 9).

Please return an ordered list of accession numbers where any consecutive digits N...N that share the same prefix L...L are collapsed into an accession number range.

An accession number range has the following format : L...LN...N-L...LN...N (e.g. A00001-A00005).

Please note that the N...N digits are left padded using 0s (e.g. 00001) and that the length of the N...N digits must be the same for accession numbers to be part of the same accession number range.

Example input:
A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1

Expected output:
A00000, A0001, ABCDEFG1, DRR2110012, ERR000111-ERR000113, ERR000115-ERR000116, ERR100114, ERR200000001-ERR200000003, SRR211001 


## Getting Started

1. Copy AccessionNumberProject.zip. Unzip it to get folder AccessionNumberProject.
2. AccessionNumberProject folder will have AccessionNumber folder which will have source code and JUNIT test cases for AccessionNumber library. Folder structure is same structure as gradle project. Gradle format of project has been created using gradle plugin in Eclipse Java IDE. Source code can be copied and used in eclipse Java IDE itself.
3. AccessionNumber folder will have bin folder which has library file AccessionNumber.jar
4. AccessionNumberProject folder will have ACNDriver folder which will have source code of driver program to run and test Accession Number functionality. It has source code to test using command prompt and using thin embedded web server mode which has been written.
5. ACNDriver folder will have bin folder which has jar file ACNDriver.jar to run and test the accession number program.
6. Open command prompt. Go to Java - jdk 8 location. Type command : java - jar "<Location of AccessionNumberProject\ACNDriver folder >\bin\ACNDriver.jar". Follow the instructions to order any given list of accession numbers which is provided as input. It provides option to test accession numbers ordering using command prompt and also using embedded thin web server.
7. In case of embedded thin web server mode. Use http testing tool e.g. Postman to provide list of accession numbers. Web Endpoint is http://localhost:<portnum>. Provide list of accession numbers as body of POST method.
8. AccessionNumberProject folder will have README.md too.


### Prerequisites
To run the program you need to:
Install Java JDK 8

To look at the code with gradle project structure you need to:
Install Java eclipse IDE.
Install Java JDK 8
Install gradle eclipse plugin. Please refer http://www.journaldev.com/8118/gradle-eclipse-plugin-tutorial

### Installation

To look at the code with gradle project structure you need to:
Install Java eclipse IDE.
Install Java JDK 8
Install gradle eclipse plugin. Please refer http://www.journaldev.com/8118/gradle-eclipse-plugin-tutorial for instructions.
Create two projects one for  AccessionNumber library and another for driver program ACNDriver in eclipse and import complete source code provided.
Use gradle to build. It will compile and build the project code, generate binaries, and run the JUNIT test cases for AccessionNumber project. ACNDriver is driver program so no JUNIT has been added to it.
You can also refer AccessionNumberProjectStructure.png and ACNDriverProjectStructure.png to see how project structure look like in eclipse.

## Running the tests

To run and execute test cases:
You can also use command prompt to run the program and provide test data to it.
Open command prompt. Go to Java - jdk 8 location. Type command java - jar "<Location of AccessionNumberProject\ACNDriver folder >\bin\ACNDriver.jar". Follow the instructions to order any given list of accession numbers which is provided as input.
It provides option to test accession numbers ordering using command prompt and also using embedded thin web server.
In case of embedded thin web server mode. Use http testing tool e.g. Postman to provide list of accession numbers. Provide list of accession numbers as body of POST method. Web Endpoint is http://localhost:<portnum>
Example of data in body of POST:
AA12AA, A00100, A00099, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1
Please refer PostmanInput.png to see how URL and body input should look like.

To run JUNIT test cases using eclipse:
Once what is said in [Installation] is done, you should be able to run JUNIT test cases of Accession Numbers as well.


### Test Cases

Example of accession numbers test case data:
1. AA12AA, A00100, A00099, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1
2. A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1
3. A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, }}2, }}1
4. AABCDEFG1
5. AABCDEFG1, }}100010, }}100009
6. ###!!!100010, ###!!!100009, ###!!!100015
7. ###ABC100016, ###ABC100020, ###ABC100019
8. ###ABC100016, ###ABC100020, ###ABC100018
9. ""
10. null

### Coding style JUNIT tests

Many unit test cases has been covered using JUNIT.

Examples of JUNIT:
	@Test
	public void getOrderedAccessionList1() {
		String slist = "AA12AA, A00100, A00099, A0001, ERR000111, ERR000112, "
				+ "ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, "
				+ "ERR200000003, DRR2110012, SRR211001, ABCDEFG1";
		String str = AccessionNumberProcessor.getSortedList(slist);
		assertEquals("A0001, A00099-A00100, ABCDEFG1, DRR2110012, "
				+ "ERR000111-ERR000113, ERR000115-ERR000116, ERR100114, " + "ERR200000001-ERR200000003, SRR211001",
				str);
	}

	@Test
	public void getOrderedAccessionList2() {
		String slist = "A00000, A0001, ERR000111, ERR000112, ERR000113, "
				+ "ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, "
				+ "ERR200000003, DRR2110012, SRR211001, ABCDEFG1";
		assertEquals(
				"A00000, A0001, ABCDEFG1, DRR2110012, ERR000111-ERR000113, "
						+ "ERR000115-ERR000116, ERR100114, ERR200000001-ERR200000003, SRR211001",
				AccessionNumberProcessor.getSortedList(slist));
	}
	@Test
	public void getOrderedAccessionListNeg() {
		String slist = "";
		String str = AccessionNumberProcessor.getSortedList(slist);
		assertEquals("", str);
	}

	@Test
	public void getOrderedAccessionListNull() {
		assertEquals(null, AccessionNumberProcessor.getSortedList(null));
	}

## Deployment

On live system, this AccessionNumberProject\AccessionNumber\bin\AccessionNumber.jar can be deployed and any module who wants to get ordered list of accession numbers can call public API: String AccessionNumberProcessor.getSortedList(String).
Please pass list of accession numbers which you want to order as string and each  number is seperated by comma. Return is also string which is ordered list of accession numbers.
Example:
Input:
AA12AA, A00100, A00099, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1
Output:
A0001, A00099-A00100, ABCDEFG1, DRR2110012, ERR000111-ERR000113, ERR000115-ERR000116, ERR100114, ERR200000001-ERR200000003, SRR211001

## Built With

* Eclipse Java IDE, Java 8
* Gradle-Eclipse plugin

## TODO
Log4j integration is not done yet to get the most appropriate log functionality.

## Contributing

This project is yet to be submitted to github. Once it is done, I shall publish ways to contribute.

## Versioning

This project is yet to be submitted to github. Once it is done, I shall publish about versioning.

## Authors

* **Azaharuddin**

## License

This project is not licensed yet, once I will submitt, I will put note about license. Feel free to use it.

## Acknowledgments

* Java 8 collection APIs
* Gradle plugin for eclipse java IDE : http://www.journaldev.com/8118/gradle-eclipse-plugin-tutorial
