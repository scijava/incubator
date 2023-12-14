###
# #%L
# ImgLib2: a general-purpose, multidimensional image processing library.
# %%
# Copyright (C) 2009 - 2016 Tobias Pietzsch, Stephan Preibisch, Stephan Saalfeld,
# John Bogovic, Albert Cardona, Barry DeZonia, Christian Dietz, Jan Funke,
# Aivar Grislis, Jonathan Hale, Grant Harris, Stefan Helfrich, Mark Hiner,
# Martin Horn, Steffen Jaensch, Lee Kamentsky, Larry Lindsey, Melissa Linkert,
# Mark Longair, Brian Northan, Nick Perry, Curtis Rueden, Johannes Schindelin,
# Jean-Yves Tinevez and Michael Zinsmaier.
# %%
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as
# published by the Free Software Foundation, either version 2 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public
# License along with this program.  If not, see
# <http://www.gnu.org/licenses/gpl-2.0.html>.
# #L%
###

# Transforms CSV data into HTML that uses pChart4mw charts.

# Charts produced:
#   - Iteration x Time for minimum resolution image, by method
#   - Iteration x Time for maximum resolution image, by method
#   - Resolution x Time at first iteration, by method
#   - Resolution x Time at last iteration, by method

# Total charts: 8 (4 for "cheap" and 4 for "expensive")

import math, os, string

# NB: Presumably there is a slick way to avoid having two very similar
# methods (generateIterationChart and generateResolutionChart) iterating
# over different list indices, but my Python knowledge is weak.

# Iteration x Time at a given resolution, by method
def generateIterationChart(name, data, methods, resolution_index, keywords):
    resolutions = range(1, 26, 3)
    resolution_count = len(data)
    iteration_count = len(data[0])
    method_count = len(methods)
    title = 'Iteration x Time (ms) at ' + str(resolutions[resolution_index]) + ' Mpx'
    div = "legend" not in keywords
    if div:
        print('<div style="float: left">')
    print('<pLines ymin=0 title="' + title + '" ' + keywords + '>')
    # Header
    for m in range(0, method_count):
        suffix = ',' if m < method_count - 1 else ''
        print(methods[m] + suffix,)
    print()
    # Data
    for i in range(0, iteration_count):
        for m in range(0, method_count):
            suffix = ',' if m < method_count - 1 else ''
            print(str(data[resolution_index][i][m]) + suffix)
        print()
    print('</pLines>')
    if div:
        print('</div>')

# Resolution x Time at a given iteration, by method
def generateResolutionChart(name, data, methods, iteration_index, keywords):
    resolutions = range(1, 26, 3)
    resolution_count = len(data)
    iteration_count = len(data[0])
    method_count = len(methods)
    title = 'Resolution x Time (ms) at iteration #' + str(iteration_index + 1)
    div = "legend" not in keywords
    if div:
        print('<div style="float: left">')
    print('<pLines ymin=0 title="' + title + '" ' + keywords + '>')
    # Header
    print(',')
    for m in range(0, method_count):
        suffix = ',' if m < method_count - 1 else ''
        print(methods[m] + suffix)
    print()
    # Data
    for r in range(0, resolution_count):
        print(str(resolutions[r]) + ' Mpx,')
        for m in range(0, method_count):
            suffix = ',' if m < method_count - 1 else ''
            print(str(data[r][iteration_index][m]) + suffix)
        print()
    print('</pLines>')
    if div:
        print('</div>')

# reads data from CSV files into 3D array dimensioned:
# [resolution_count][iteration_count][method_count]
def process(prefix):
    methods = []

    # loop over image resolutions
    data = []
    for p in range(1, 26, 3):
        # compute filename
        res = round(math.sqrt(1000000 * p))
        s_res = str(int(res))
        path_prefix = 'results-' + prefix + '-' + s_res + 'x' + s_res
        in_path = path_prefix + '.csv'

        # read data file
        with open(in_path, 'r') as f:
            lines = f.readlines()

        # loop over iterations
        header = True
        data0 = []
        for line in lines:
            items = line.rstrip().split('\t')
            items.pop(0)
            if header:
                header = False
                methods = items
            else:
                # loop over methods
                data1 = []
                for item in items:
                    data1.append(int(item))
                data0.append(data1)
        data.append(data0)

    resolution_count = len(data)
    iteration_count = len(data[0])
    method_count = len(methods)

    w = 300
    h = 250
    l_size = 'size=' + str(w) + 'x' + str(h)
    r_size = 'size=' + str(w + 135) + 'x' + str(h)

    # Iteration x Time for minimum resolution image, by method
    generateIterationChart('res_' + prefix + '_min', \
                           data, methods, 0, l_size + ' plots')
    # Iteration x Time for maximum resolution image, by method
    generateIterationChart('res_' + prefix + '_max', \
                           data, methods, resolution_count - 1, r_size + ' plots legend')
    print('{{Clear}}')
    # Resolution x Time at first iteration, by method
    generateResolutionChart('iter_' + prefix + '_first', \
                            data, methods, 0, l_size + ' angle=90 cubic plots')
    # Resolution x Time at last iteration, by method
    generateResolutionChart('iter_' + prefix + '_last', \
                            data, methods, iteration_count - 1, r_size + ' angle=90 cubic plots legend')
    print('{{Clear}}')

print('== Cheap operation results ==')
process('cheap')
print('== Expensive operation results ==')
process('expensive')
