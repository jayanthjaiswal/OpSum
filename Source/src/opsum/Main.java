package opsum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import util.Constants;

public class Main {

	public static void println(String out) {
		// System.err.println(out);
	}

	public static void print(String out) {
		// System.err.print(out);
	}

	public static Double round(Double doubleVal) {
		double rounded = Math.floor(1000 * doubleVal + 0.5) / 1000;
		return rounded;
	}

	public static void runSample() {
		try {
			File file = new File(Constants.CONFIG_BASE_DIR + "log.txt");
			FileOutputStream fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			System.setErr(ps);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Summarize summarizer = new Summarize();
		summarizer.scoreL = new ScoreL(summarizer);
		summarizer.scoreA = new ScoreA4(summarizer);

		// String testFile =
		// "/home/zerone/code/OpinionSummary/code/test/001.txt";
		String testFile = "/home/zerone/opsum/run04/cv000_29590.txt";

		List<Sentence> lSent = summarizer.doSummarizeFromFile(testFile);
		for (Sentence sent : lSent) {
			System.err.println(sent);
		}

		/** Format */

		List<Sentence> lSent1 = lSent;
		Collections.sort(lSent1, new Comparator<Sentence>() {
			public int compare(Sentence o1, Sentence o2) {
				if (o1.getSentID() > o2.getSentID()) {
					return 1;
				} else if (o1.getSentID() < o2.getSentID()) {
					return -1;
				}
				return 0;
			}
		});

		for (Sentence sent : lSent1) {
			System.out.println(sent.getSentID() + ")\t" + sent.getSentString());
		}

		System.out.println("Total number of words :: "
				+ summarizer.maxSubP.currBucketSize);

		// summarizer.aspectRoot.printOk("");
	}

	public static void writeBaseLines(String filename, String outPrefix) {
		try {
			
			File f = new File(outPrefix);
			System.out.println("" + System.currentTimeMillis() + "\t" + outPrefix);
			f.mkdirs();

			
			Baseline bl = new Baseline();
			bl.doSummarizeFromFile(filename);

			List<Sentence> lSent1 = bl.doProcessBaseLine1();
			String outFileName1 = outPrefix + "BASELINE1";
			System.err.println("\t\t" + outFileName1);
			PrintWriter pw1 = new PrintWriter(outFileName1);
			for (Sentence sent : lSent1) {
				pw1.print(sent.getSentString() + "\n");
			}
			pw1.close();

			List<Sentence> lSent2 = bl.doProcessBaseLine2();
			String outFileName2 = outPrefix + "BASELINE2";
			System.err.println("\t\t" + outFileName2);
			PrintWriter pw2 = new PrintWriter(outFileName2);
			for (Sentence sent : lSent2) {
				pw2.print(sent.getSentString() + "\n");
			}
			pw2.close();
			
			
			/* LIMS */

			Double startR = 0.0;
			Double endR = 1.0;
			Double intR = 0.25;
			Double Alpha = 1.0;
			for (Double R = startR; R <= endR; R += intR) {
				Summarize summarizer = new Summarize();
				summarizer.scoreA = new ScoreA1(summarizer);
				summarizer.scoreL = new ScoreL(summarizer);
				summarizer.tradeAlpha = Alpha;
				summarizer.tradeR = R;
				List<Sentence> lSent = summarizer.doSummarizeFromFile(filename);
				lSent1 = lSent;
				Collections.sort(lSent1, new Comparator<Sentence>() {
					public int compare(Sentence o1, Sentence o2) {
						if (o1.getSentID() > o2.getSentID()) {
							return 1;
						} else if (o1.getSentID() < o2.getSentID()) {
							return -1;
						}
						return 0;
					}
				});

				String outFileName = outPrefix + "LIN"+"_R" + Main.round(R);
				System.err.println("\t\t" + outFileName);
				PrintWriter pw = new PrintWriter(outFileName);
				for (Sentence sent : lSent1) {
					// System.out.println(sent.getSentID() + ")\t" +
					// sent.getSentString());
					pw.print(sent.getSentString() + "\n");
				}
				pw.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void runTestForFile(String filename, String outPrefix)
			throws Exception {
		Double startAlpha = 0.0;
		Double endAlpha = 1.0;
		Double intAlpha = 0.1;

		Double startR = 0.0;
		Double endR = 1.0;
		Double intR = 0.25;
		File f = new File(outPrefix);
		if (f.isDirectory()) {
			return;
		}
		System.out.println("" + System.currentTimeMillis() + "\t" + outPrefix);
		f.mkdirs();

		writeBaseLines(filename, outPrefix);

		for (int i = 1; i <= 6; i++) {
			if (i == 4)
				continue;
			for (Double Alpha = startAlpha; Alpha <= endAlpha; Alpha += intAlpha) {
				for (Double R = startR; R <= endR; R += intR) {

					Summarize summarizer = new Summarize();

					summarizer.scoreL = new ScoreL(summarizer);
					if (i == 1)
						summarizer.scoreA = new ScoreA1(summarizer);
					else if (i == 2)
						summarizer.scoreA = new ScoreA2(summarizer);
					else if (i == 3)
						summarizer.scoreA = new ScoreA3(summarizer);
					else if (i == 4)
						summarizer.scoreA = new ScoreA4(summarizer);
					else if (i == 5)
						summarizer.scoreA = new ScoreA5(summarizer);
					else if (i == 6)
						summarizer.scoreA = new ScoreA6(summarizer);

					summarizer.tradeAlpha = Alpha;
					summarizer.tradeR = R;

					List<Sentence> lSent = summarizer
							.doSummarizeFromFile(filename);
					List<Sentence> lSent1 = lSent;
					Collections.sort(lSent1, new Comparator<Sentence>() {
						public int compare(Sentence o1, Sentence o2) {
							if (o1.getSentID() > o2.getSentID()) {
								return 1;
							} else if (o1.getSentID() < o2.getSentID()) {
								return -1;
							}
							return 0;
						}
					});

					String outFileName = outPrefix + "A" + i + "_ALPHA"
							+ Main.round(Alpha) + "_R" + Main.round(R);
					System.err.println("\t\t" + outFileName);
					PrintWriter pw = new PrintWriter(outFileName);
					for (Sentence sent : lSent1) {
						// System.out.println(sent.getSentID() + ")\t" +
						// sent.getSentString());
						pw.print(sent.getSentString() + "\n");
					}
					pw.close();

					/*
					 * if(ccnt>0) System.exit(0); ccnt++;
					 */
				}
			}
		}
	}

	public static List<Sentence> getSummary(Integer iFunc, Double Alpha,
			Double R, int budget, String text) throws Exception {

		Summarize summarizer = new Summarize();

		summarizer.bucketSize = budget;
		summarizer.scoreL = new ScoreL(summarizer);
		if (iFunc == 1)
			summarizer.scoreA = new ScoreA1(summarizer);
		else if (iFunc == 2)
			summarizer.scoreA = new ScoreA2(summarizer);
		else if (iFunc == 3)
			summarizer.scoreA = new ScoreA3(summarizer);
		else if (iFunc == 4)
			summarizer.scoreA = new ScoreA4(summarizer);
		else if (iFunc == 5)
			summarizer.scoreA = new ScoreA5(summarizer);
		else if (iFunc == 6)
			summarizer.scoreA = new ScoreA6(summarizer);

		summarizer.tradeAlpha = Alpha;
		summarizer.tradeR = R;

		List<Sentence> lSent = summarizer.doSummarizeFromText(text);
		List<Sentence> lSent1 = lSent;
		Collections.sort(lSent1, new Comparator<Sentence>() {
			public int compare(Sentence o1, Sentence o2) {
				if (o1.getSentID() > o2.getSentID()) {
					return 1;
				} else if (o1.getSentID() < o2.getSentID()) {
					return -1;
				}
				return 0;
			}
		});

		return lSent1;
	}

	public static void runTest() {
/*
		try {
			File file = new File(Constants.CONFIG_BASE_DIR + "log.txt");
			FileOutputStream fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			System.setErr(ps);
		} catch (Exception e) {
			e.printStackTrace();
		}
*/
		String dir = "/home/zerone/opsum/run41/";
		for (File d : util.IDF.FindTextFiles(dir)) {
			try {
				String sFile = d.getAbsolutePath();
				System.err.println(sFile);
				//runTestForFile(sFile, sFile + ".summ/");
				writeBaseLines(sFile, sFile + ".summ/");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			runTest();
			// runSample();
			// String sFile = "/home/zerone/opsum/run04/cv001_18431.txt";
			// runTestForFile(sFile, sFile + ".summ/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
