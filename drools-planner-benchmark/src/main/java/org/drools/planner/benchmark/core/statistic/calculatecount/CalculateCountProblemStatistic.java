/*
 * Copyright 2011 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.planner.benchmark.core.statistic.calculatecount;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.drools.planner.benchmark.core.ProblemBenchmark;
import org.drools.planner.benchmark.core.SingleBenchmark;
import org.drools.planner.benchmark.core.statistic.AbstractProblemStatistic;
import org.drools.planner.benchmark.core.statistic.MillisecondsSpendNumberFormat;
import org.drools.planner.benchmark.core.statistic.ProblemStatisticType;
import org.drools.planner.benchmark.core.statistic.SingleStatistic;
import org.drools.planner.benchmark.core.statistic.bestscore.BestScoreSingleStatistic;
import org.drools.planner.benchmark.core.statistic.bestscore.BestScoreSingleStatisticPoint;
import org.drools.planner.core.Solver;
import org.drools.planner.core.score.Score;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class CalculateCountProblemStatistic extends AbstractProblemStatistic {

    public CalculateCountProblemStatistic(ProblemBenchmark problemBenchmark) {
        super(problemBenchmark, ProblemStatisticType.CALCULATE_COUNT_PER_SECOND);
    }

    public SingleStatistic createSingleStatistic(Solver solver) {
        return new CalculateCountSingleStatistic(solver);
    }

    // ************************************************************************
    // Write methods
    // ************************************************************************

    protected void writeCsvStatistic() {
        ProblemStatisticCsv csv = new ProblemStatisticCsv();
        for (SingleBenchmark singleBenchmark : problemBenchmark.getSingleBenchmarkList()) {
            CalculateCountSingleStatistic singleStatistic = (CalculateCountSingleStatistic)
                    singleBenchmark.getSingleStatistic(problemStatisticType);
            for (CalculateCountSingleStatisticPoint point : singleStatistic.getPointList()) {
                long timeMillisSpend = point.getTimeMillisSpend();
                csv.addPoint(singleBenchmark, timeMillisSpend, point.getCalculateCountPerSecond());
            }
        }
        csvStatisticFile = new File(problemBenchmark.getProblemReportDirectory(),
                problemBenchmark.getName() + "CalculateCountStatistic.csv");
        csv.writeCsvStatisticFile();
    }

    protected void writeGraphStatistic() {
        XYSeriesCollection seriesCollection = new XYSeriesCollection();
        for (SingleBenchmark singleBenchmark : problemBenchmark.getSingleBenchmarkList()) {
            CalculateCountSingleStatistic singleStatistic = (CalculateCountSingleStatistic)
                    singleBenchmark.getSingleStatistic(problemStatisticType);
            XYSeries series = new XYSeries(singleBenchmark.getSolverBenchmark().getName());
            for (CalculateCountSingleStatisticPoint point : singleStatistic.getPointList()) {
                long timeMillisSpend = point.getTimeMillisSpend();
                long calculateCountPerSecond = point.getCalculateCountPerSecond();
                series.add(timeMillisSpend, calculateCountPerSecond);
            }
            seriesCollection.addSeries(series);
        }
        NumberAxis xAxis = new NumberAxis("Time spend");
        xAxis.setNumberFormatOverride(new MillisecondsSpendNumberFormat());
        NumberAxis yAxis = new NumberAxis("Calculate count per second");
        yAxis.setAutoRangeIncludesZero(false);
        XYItemRenderer renderer = new XYLineAndShapeRenderer();
        XYPlot plot = new XYPlot(seriesCollection, xAxis, yAxis, renderer);
        plot.setOrientation(PlotOrientation.VERTICAL);
        JFreeChart chart = new JFreeChart(problemBenchmark.getName() + " calculate count statistic",
                JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        BufferedImage chartImage = chart.createBufferedImage(1024, 768);
        graphStatisticFile = new File(problemBenchmark.getProblemReportDirectory(),
                problemBenchmark.getName() + "CalculateCountStatistic.png");
        OutputStream out = null;
        try {
            out = new FileOutputStream(graphStatisticFile);
            ImageIO.write(chartImage, "png", out);
        } catch (IOException e) {
            throw new IllegalArgumentException("Problem writing graphStatisticFile: " + graphStatisticFile, e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

}
