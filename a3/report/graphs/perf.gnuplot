set xlabel 'Corpus Size (No. Sentences)'
set ylabel 'AER'
set term postscript enhanced 18
set output 'perf_aer.eps'
set key right top 
#set size 0.8,0.8
set size 0.7, 0.7
set yrange [0.0:1.0]
set logscale x

plot 'perf_