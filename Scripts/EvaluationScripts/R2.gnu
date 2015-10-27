name=system("echo $FILE")

set terminal postscript eps enhanced color font 'Helvetica,10'
set title "Alpha vs ROUGE-1 F-Score"
set xlabel "Alpha"
set ylabel "ROUGE-1"
#set xtics 0.0,+0.1,1.0
show grid


set output name.".avgF.R0.0.ps"  
plotfile= name.".R0.0.avgF.data"
plot plotfile u 2:3 t 'A1' with linespoints    lw 3 pt 1 ps 1,\
 plotfile u 2:4 t 'A2' with linespoints    lw 3 pt 1 ps 1, \
 plotfile u 2:5 t 'A3' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:7 t 'A4' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:8 t 'A5' with linespoints    lw 3 pt 1 ps 1, 

set output name.".avgF.R0.25.ps"  
plotfile= name.".R0.25.avgF.data"
plot plotfile u 2:3 t 'A1' with linespoints    lw 3 pt 1 ps 1,\
 plotfile u 2:4 t 'A2' with linespoints    lw 3 pt 1 ps 1, \
 plotfile u 2:5 t 'A3' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:7 t 'A4' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:8 t 'A5' with linespoints    lw 3 pt 1 ps 1, 

set output name.".avgF.R0.5.ps"  
plotfile= name.".R0.5.avgF.data"
plot plotfile u 2:3 t 'A1' with linespoints    lw 3 pt 1 ps 1,\
 plotfile u 2:4 t 'A2' with linespoints    lw 3 pt 1 ps 1, \
 plotfile u 2:5 t 'A3' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:7 t 'A4' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:8 t 'A5' with linespoints    lw 3 pt 1 ps 1, 

set output name.".avgF.R0.75.ps"  
plotfile= name.".R0.75.avgF.data"
plot plotfile u 2:3 t 'A1' with linespoints    lw 3 pt 1 ps 1,\
 plotfile u 2:4 t 'A2' with linespoints    lw 3 pt 1 ps 1, \
 plotfile u 2:5 t 'A3' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:7 t 'A4' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:8 t 'A5' with linespoints    lw 3 pt 1 ps 1, 

set output name.".avgF.R1.0.ps"  
plotfile= name.".R1.0.avgF.data"
plot plotfile u 2:3 t 'A1' with linespoints    lw 3 pt 1 ps 1,\
 plotfile u 2:4 t 'A2' with linespoints    lw 3 pt 1 ps 1, \
 plotfile u 2:5 t 'A3' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:7 t 'A4' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:8 t 'A5' with linespoints    lw 3 pt 1 ps 1,


set title "Alpha vs Sentiment Correlation "
set ylabel "Sentiment Correlation (Document and Summary)"
set xlabel "Alpha"
set xtics 0.0,+0.1,1.0

set output name.".senti.R0.0.ps"  
plotfile= name.".R0.0.senti.data"
plot plotfile u 2:3 t 'A1' with linespoints    lw 3 pt 1 ps 1,\
 plotfile u 2:4 t 'A2' with linespoints    lw 3 pt 1 ps 1, \
 plotfile u 2:5 t 'A3' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:7 t 'A4' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:8 t 'A5' with linespoints    lw 3 pt 1 ps 1, 

set output name.".senti.R0.25.ps"  
plotfile= name.".R0.25.senti.data"
plot plotfile u 2:3 t 'A1' with linespoints    lw 3 pt 1 ps 1,\
 plotfile u 2:4 t 'A2' with linespoints    lw 3 pt 1 ps 1, \
 plotfile u 2:5 t 'A3' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:7 t 'A4' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:8 t 'A5' with linespoints    lw 3 pt 1 ps 1, 

set output name.".senti.R0.5.ps"  
plotfile= name.".R0.5.senti.data"
plot plotfile u 2:3 t 'A1' with linespoints    lw 3 pt 1 ps 1,\
 plotfile u 2:4 t 'A2' with linespoints    lw 3 pt 1 ps 1, \
 plotfile u 2:5 t 'A3' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:7 t 'A4' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:8 t 'A5' with linespoints    lw 3 pt 1 ps 1, 

set output name.".senti.R0.75.ps"  
plotfile= name.".R0.75.senti.data"
plot plotfile u 2:3 t 'A1' with linespoints    lw 3 pt 1 ps 1,\
 plotfile u 2:4 t 'A2' with linespoints    lw 3 pt 1 ps 1, \
 plotfile u 2:5 t 'A3' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:7 t 'A4' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:8 t 'A5' with linespoints    lw 3 pt 1 ps 1, 

set output name.".senti.R1.0.ps"  
plotfile= name.".R1.0.senti.data"
plot plotfile u 2:3 t 'A1' with linespoints    lw 3 pt 1 ps 1,\
 plotfile u 2:4 t 'A2' with linespoints    lw 3 pt 1 ps 1, \
 plotfile u 2:5 t 'A3' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:7 t 'A4' with linespoints     lw 3 pt 1 ps 1, \
 plotfile u 2:8 t 'A5' with linespoints    lw 3 pt 1 ps 1, 

