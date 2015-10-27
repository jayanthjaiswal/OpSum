import sys, glob

manual_ext={'A':'.A'};

path=sys.argv[1];
sent_del = "\n";

def get_all_eval(path):
	lfiles=glob.glob(path+"/*.txt");
	lfiles.sort();
	return lfiles;


def get_all_peers(filename):
	files=glob.glob(filename+".summ/*");
	files.sort();
	res="";
	for file1 in files:
		pid=file1.rfind('/')+1;
		pid=file1[pid:]
		res=res + "<P ID=\""+pid+"\">"+file1+"</P>"+sent_del;
	return res;

def get_all_models(filename):
	res="";
	for ext in manual_ext:
		val=manual_ext[ext];
		res=res+"<M ID=\""+ext+"\">"+ filename+val +"</M>"+sent_del;
	return res;


def const_eval(evalid, filename):
	res="";
	res=res+"<EVAL ID=\""+str(evalid)+"\">"+sent_del;
	res=res+"<PEER-ROOT>"+sent_del;
	res=res+"/home/zerone/opsum"+sent_del;
	res=res+"</PEER-ROOT>"+sent_del
	res=res+"<MODEL-ROOT>"+sent_del
	res=res+"/home/zerone/opsum"+sent_del
	res=res+"</MODEL-ROOT>"+sent_del
	res=res+"<INPUT-FORMAT TYPE=\"SPL\">"+sent_del
	res=res+"</INPUT-FORMAT>"+sent_del
	res=res+"<PEERS>"+sent_del
	res=res+get_all_peers(filename)
	res=res+"</PEERS>"+sent_del
	res=res+"<MODELS>"+sent_del
	res=res+get_all_models(filename)
	res=res+"</MODELS>"+sent_del
	res=res+"</EVAL>"+sent_del
	return res;
	
def main():
	lfiles=get_all_eval(path);
	evalid=1;
	res="<ROUGE-EVAL version=\"1.0\">";
	for fil in lfiles:
		res=res+const_eval(evalid, fil);
		evalid=evalid+1;
	res=res+"</ROUGE-EVAL>"	
	print res


main();
	
