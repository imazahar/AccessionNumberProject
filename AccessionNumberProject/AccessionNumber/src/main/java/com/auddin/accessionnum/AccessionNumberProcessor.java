package com.auddin.accessionnum;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

public class AccessionNumberProcessor {

	public static String getSortedList(String slist) {
		if(slist == null){
			return null;
		}
		if (slist.isEmpty()) {
			return "";
		}
		
		try {
			String[] args = slist.split(",");
			TreeMap<String, TreeSet<String>> tMap = new TreeMap<String, TreeSet<String>>();
			for (int i = 0; i < args.length; i++) {
				String[] arr = isAccessionNumber(args[i].trim());
				if (arr != null && !arr[0].isEmpty() && !arr[1].isEmpty()) {
					TreeSet<String> stList = null;
					if (tMap.containsKey(arr[0])) {
						stList = tMap.get(arr[0]);
					} else {
						stList = new TreeSet<String>();
					}
					stList.add(arr[1]);
					tMap.put(arr[0], stList);
				} else {
					System.out.println(
							System.getProperty("line.separator") + args[i].trim() + " is not valid accession number");
				}
			}

			LinkedList<String> returnList = createOrderedList(tMap);
			String strResult = returnList.toString().substring(1, returnList.toString().length() -1);
			return strResult;
		} catch (Exception e) {
			// TODO: Log information in Log Files using Log4j
			return "";
		}
	}

	/**
	 * @param tMap
	 * @return
	 */
	private static LinkedList<String> createOrderedList(TreeMap<String, TreeSet<String>> tMap) {
		try {
			LinkedList<String> returnList = new LinkedList<String>();
			LinkedList<String> sortlist = new LinkedList<String>();
			for (Entry<String, TreeSet<String>> entry : tMap.entrySet()) {
				String key = entry.getKey();
				TreeSet<String> value = entry.getValue();

				Iterator<String> iterator;
				iterator = value.iterator();
				String str1 = "", str2 = "";

				if (value.size() == 1) {
					returnList.add(key + value.first());
				}
				if (value.size() == 2) {
					if (value.first().length() == value.last().length() && compareForOne(value.last(), value.first())) {
						returnList.add(key + value.first() + "-" + key + value.last());
					} else {
						returnList.add(key + value.first());
						returnList.add(key + value.last());
					}
				}
				while (iterator.hasNext() && value.size() > 2) {
					if (!str1.isEmpty() && !str2.isEmpty()) {
						if (str1.length() == str2.length() && compareForOne(str2, str1)) {
							sortlist.addLast(str1);
							sortlist.addLast(str2);
						} else {
							if (sortlist.size() >= 2 && sortlist.getFirst() != null && sortlist.getLast() != null) {
								returnList.add(key + sortlist.getFirst() + "-" + key + sortlist.getLast());
								sortlist.clear();
							} else {
								returnList.add(key + str1);
							}
						}
						str1 = str2;
						str2 = "";
					} else {
						String strTmp = iterator.next();
						if (str1.isEmpty()) {
							str1 = strTmp;
						} else if (str2.isEmpty()) {
							str2 = strTmp;
						}
					}
				}
				if (!str1.isEmpty() && !str2.isEmpty()) {
					if (str1.length() == str2.length() && compareForOne(str2, str1)) {
						sortlist.addLast(str1);
						sortlist.addLast(str2);
					} else {
						if (sortlist.size() >= 2 && sortlist.getFirst() != null && sortlist.getLast() != null) {
							returnList.add(key + sortlist.getFirst() + "-" + key + sortlist.getLast());
						} else {
							returnList.add(key + str1);
						}
						returnList.add(key + str2);
						sortlist.clear();
					}
				}
				if (sortlist.size() >= 2 && sortlist.getFirst() != null && sortlist.getLast() != null) {
					returnList.add(key + sortlist.getFirst() + "-" + key + sortlist.getLast());
				}
				sortlist.clear();
			}
			return returnList;
		} catch (Exception e) {
			// TODO: Log information in Log Files using Log4j
			return null;
		}
	}

	private static String[] isAccessionNumber(String arg) {
		if (arg == null || arg.isEmpty()) {
			System.out.println("Incorrect accession number");
			return null;
		}
		String[] strArr = new String[2];
		try {
			strArr[0] = "";
			strArr[1] = "";
			StringBuilder sBKey = new StringBuilder();
			StringBuilder sBVal = new StringBuilder();
			for (int i = 0; i < arg.length(); i++) {
				char c = arg.charAt(i);
				int val = (int) c;
				if (val >= 0 && val <= 127) {
					if (Character.isDigit(c)) {
						if (strArr[0].isEmpty() && sBKey.length() > 0) {
							strArr[0] = sBKey.toString();
							sBKey.delete(0, sBKey.length() - 1);
						}
						if (strArr[0] == null) {
							return null;
						}
						sBVal.append(c);
					} else {
						if (!strArr[0].isEmpty()) {
							return null;
						}
						sBKey.append(c);
					}
				} else {
					return null;
				}
			}
			if (strArr[1].isEmpty() && sBVal.length() > 0) {
				strArr[1] = sBVal.toString();
			}
		} catch (Exception ex) {
			// TODO: Log information in Log Files using Log4j
			return null;
		} finally {
			// TODO: Log information in Log Files using Log4j
		}
		return strArr;
	}

	private static boolean compareForOne(String str1, String str2) {
		try {
			LinkedList<Integer> numData1 = new LinkedList<Integer>();
			LinkedList<Integer> numData2 = new LinkedList<Integer>();
			LinkedList<Integer> numTemp = new LinkedList<Integer>();
			LinkedList<Integer> numAns = new LinkedList<Integer>();
			boolean numSign = false;		
			String strInFn1 = str1, strInFn2 = str2;
			int str1ln = str1.length(), str2ln = str2.length();
			
			if (str1ln < str2ln || (str1ln == str2ln && str1.compareTo(str2) < 0)) {
				strInFn1 = str2;
				strInFn2 = str1;
				numSign = true;
			}
			str1ln = strInFn1.length();
			while (strInFn2.length() != str1ln)
				strInFn2 = "0" + strInFn2;
			for (int i = str1ln - 1; i >= 0; i--) {
				numData1.add(strInFn1.charAt(i) - '0');
				numData2.add('9' - strInFn2.charAt(i));
			}
			int numCarry = 0, digit1 = 0, digit2 = 0;
			for (int i = 0; i < str1ln; i++) {
				try {
					digit1 = numData1.get(i);
				} catch (Exception e) {
					// TODO: Log information in Log Files using Log4j
					return false;
				}
				try {
					digit2 = numData2.get(i);
				} catch (Exception e) {
					// TODO: Log information in Log Files using Log4j
					return false;
				}
				int calc = digit1 + digit2 + numCarry;
				numTemp.add(calc % 10);
				numCarry = calc / 10;
			}
			for (int i = 0; i < str1ln; i++) {
				int calc = numTemp.get(i) + numCarry;
				numAns.add(calc % 10);
				numCarry = calc / 10;
			}
			if (strInFn1.equals(strInFn2))
				return false;
			else {
				if (numSign)
					return false;
				boolean bret = false;
				for (int i = 0; i < numAns.size(); i++) {
					if (i == 0 && numAns.get(0) == 1) {
						bret = true;
					} else if (i == 0 && numAns.get(0) != 1) {
						bret = false;
						break;
					} else {
						bret = numAns.get(i) == 0 ? true : false;
					}
				}

				return bret;
			}
		} catch (Exception ex) {
			// TODO: Log information in Log Files using Log4j
		} finally {
			// TODO: Log information in Log Files using Log4j
		}
		return false;
	}
}
