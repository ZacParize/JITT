package com.britebill.interview.statistics.writers;

import com.britebill.interview.statistics.beans.Statistics;

import java.io.File;


public interface StatisticsWriter {
    void write( Statistics statistics, File file );
}
