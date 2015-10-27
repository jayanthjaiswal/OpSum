#!/bin/bash
set -x #echo on

export FILE=run40

cd '/home/zerone/opsum'
#java -jar summary.jar
python normalize.py ${FILE}/ 
python evaluate.py ${FILE}/ > ${FILE}.xml
RELEASE-1.5.5/ROUGE-1.5.5.pl -e RELEASE-1.5.5/data -c 95 -2 -1 -U -r 1000 -n 4 -w 1.2 -a ${FILE}.xml > ${FILE}.output
grep "ROUGE-1 Average_F:" ${FILE}.output > ${FILE}.avgF

cd '/home/zerone/code/OpinionSummary/scripts'
python jp.py

cd '/home/zerone/opsum'
python graph2.py < ${FILE}.avgF 
gnuplot R2.gnu

