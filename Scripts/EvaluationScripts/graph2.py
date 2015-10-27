import sys, math
import os
from pydoc import help
from scipy.stats.stats import pearsonr

data={};
delim="\t";
linemarker='';
name=os.environ['FILE']

k1set=set();
k2set=set();
k3set=set();

kbases=set();

#A2_ALPHA0.4_R0.75

def main():
	for line in sys.stdin:
		arr=line.split(' ');
		key=arr[0];
		value=arr[1+2];
		keytuple=key.split('_');
		if(len(keytuple)==3):
			k1=keytuple[0]
			k2=keytuple[1]
			k3=keytuple[2]
			if( k1 not in data):
				data[k1]={};
			if( k2 not in data[k1]):
				data[k1][k2]={};
			if( k3 not in data[k1][k2]):
				data[k1][k2][k3]=value;
			k1set.add(k1);
			k2set.add(k2);
			k3set.add(k3);
		else:
			k1=keytuple;
			data[k1]=value;
			kbases.add(k1);

def print3():
	for R in sorted(k3set):
		R=R.replace('R', '');
		R=float(R)
		filename=name+'.R'+str( R ) + '.avgF.data';
		fil=open(filename, 'w');
		for ALPHA in sorted(k2set):
			ALPHA=ALPHA.replace('ALPHA', '');
			ALPHA=float(ALPHA)
			res = str(R) + delim;
			res = res + str(ALPHA)+delim;
			for A in sorted(k1set):
				A=A.replace('A', '');
				A=float(A)
				k1='A'+str(int(A))
				k2='ALPHA'+str(ALPHA)
				k3='R'+str(R)
				res = res + str( data[k1][k2][k3] ) + delim;
			fil.write(res[:-1]+"\n");
		fil.close();
	for BASE in sorted(kbases):
		filename=name+ '.'+BASE+'.avgF.data';
		fil=open(filename, 'w');	
		res=str( data[BASE] ) + '\n';
		fil.write(res)
		fil.close();
		

def print4():
	for R in sorted(k3set):
		R=R.replace('R', '');
		R=float(R)
		filename=name+'.R'+str( R ) + '.senti.data';
		fil=open(filename, 'w');
		for ALPHA in sorted(k2set):
			ALPHA=ALPHA.replace('ALPHA', '');
			ALPHA=float(ALPHA)
			res = str(R) + delim;
			res = res + str(ALPHA)+delim;
			for A in sorted(k1set):
				A=A.replace('A', '');
				A=float(A)
				k1='A'+str(int(A))
				k2='ALPHA'+str(ALPHA)
				k3='R'+str(R)
				res = res + str( pearsonr(dataX[k1][k2][k3], dataY[k1][k2][k3])[0] ) + delim;
			fil.write(res[:-1]+"\n");
		fil.close();
	for BASE in sorted(kbases):
		filename=name+ '.'+BASE+'.senti.data';
		fil=open(filename, 'w');	
		res=str( pearsonr(dataX[BASE], dataY[BASE])[0] ) + '\n';
		fil.write(res)
		fil.close();

def print1():
	for R in [0.0, 0.25, 0.5, 0.75, 1.0]:
		filename=name+'.R'+str( R/0.25 ) + '.avgF.data';
		fil=open(filename, 'w');
		for ALPHA in map(lambda x: x/10.0,range(0,10)):
			res = str(R) + delim;
			res = res + str(ALPHA)+delim;
			for A in range(1,7):
				k1='A'+str(A)
				k2='ALPHA'+str(ALPHA)
				k3='R'+str(R)
				res = res + str( data[k1][k2][k3] ) + delim;
			fil.write(res[:-1]+"\n");
		fil.close();


def print2():
	for R in [0.0, 0.25, 0.5, 0.75, 1.0]:
		filename=name+'.R'+str( R/0.25 ) + '.senti.data';
		fil=open(filename, 'w');
		for ALPHA in map(lambda x: x/10.0,range(0,10)):
			res = str(R) + delim;
			res = res + str(ALPHA)+delim;
			for A in range(1,7):
				k1='A'+str(A)
				k2='ALPHA'+str(ALPHA)
				k3='R'+str(R)
				res = res + str( pearsonr(dataX[k1][k2][k3], dataY[k1][k2][k3])[0] ) + delim;
			fil.write(res[:-1]+"\n");
		fil.close();


dataX={}
dataY={}

import pickle
from scipy.stats.stats import pearsonr
handle=open("/home/zerone/opsum/"+name+'.senti.dataX.pickle', 'rb')
dataX=pickle.load(handle)
handle=open("/home/zerone/opsum/"+name+'.senti.dataY.pickle', 'rb')
dataY=pickle.load(handle)

#print dataX
#print dataY
			
main()
print3();
print4();


