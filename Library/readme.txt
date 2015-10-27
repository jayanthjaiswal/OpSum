Using Opinion Summarizer as Library
-----------------------------------------------------------

1) import opsum.jar in eclipse.

2) add dependent libraries 		

3) create /opsum.properties file with configdir=<path_to_resource_directory>


		Example:
		██ 17:03:40 $ cat /opsum.properties
		#Fri Jan 17 22:37:45 MYT 2014
		configdir=/home/zerone/code/OpinionSummary/res/


4)	Finally use the following snippet to generate the summary
 
	Example code :

		Summarize summarizer = new Summarize();

		summarizer.bucketSize = budget;
		summarizer.scoreL = new ScoreL(summarizer);
		summarizer.scoreA = new ScoreA1(summarizer);
		summarizer.tradeAlpha = 0.1;
		summarizer.tradeR = 0.1;

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


