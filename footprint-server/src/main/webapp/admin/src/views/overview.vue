<style>
</style>

<template>
    <!-- Breadcrumbs line -->
    <div class="crumbs">
        <ul id="breadcrumbs" class="breadcrumb">
            <li>
                <i class="icon-home"></i>
                <a v-link="{path:'/platform/job_list'}">Dashboard</a>
            </li>
            <li class="current">
                <a href="pages_calendar.html" title="">Calendar</a>
            </li>
        </ul>

        <ul class="crumb-buttons">
            <li><a href="charts.html" title=""><i class="icon-signal"></i><span>Statistics</span></a></li>
            <li class="dropdown"><a href="#" title="" data-toggle="dropdown"><i class="icon-tasks"></i><span>Users <strong>(+3)</strong></span><i class="icon-angle-down left-padding"></i></a>
                <ul class="dropdown-menu pull-right">
                    <li><a href="form_components.html" title=""><i class="icon-plus"></i>Add new User</a></li>
                    <li><a href="tables_dynamic.html" title=""><i class="icon-reorder"></i>Overview</a></li>
                </ul>
            </li>
            <li class="range"><a href="#">
                <i class="icon-calendar"></i>
                <span></span>
                <i class="icon-angle-down"></i>
            </a></li>
        </ul>
    </div>
    <!-- /Breadcrumbs line -->

    <!--=== Page Header ===-->
    <div class="page-header">
        <div class="page-title">
            <h3>Dashboard - shared on weidea.net</h3>
            <span>Good morning, John!</span>
        </div>

        <!-- Page Stats -->
        <ul class="page-stats">
            <li>
                <div class="summary">
                    <span>New orders</span>
                    <h3>17,561</h3>
                </div>
                <div id="sparkline-bar" class="graph sparkline hidden-xs">20,15,8,50,20,40,20,30,20,15,30,20,25,20</div>
                <!-- Use instead of sparkline e.g. this:
                <div class="graph circular-chart" data-percent="73">73%</div>
                -->
            </li>
            <li>
                <div class="summary">
                    <span>My balance</span>
                    <h3>$21,561.21</h3>
                </div>
                <div id="sparkline-bar2" class="graph sparkline hidden-xs">20,15,8,50,20,40,20,30,20,15,30,20,25,20</div>
            </li>
        </ul>
        <!-- /Page Stats -->
    </div>
    <!-- /Page Header -->

    <!--=== Page Content ===-->
    <!--=== Statboxes ===-->
    <div class="row row-bg"> <!-- .row-bg -->
        <div class="col-sm-6 col-md-3">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual cyan">
                        <div class="statbox-sparkline">30,20,15,30,22,25,26,30,27</div>
                    </div>
                    <div class="title">Clients</div>
                    <div class="value">4 501</div>
                    <a class="more" href="javascript:void(0);">View More <i class="pull-right icon-angle-right"></i></a>
                </div>
            </div> <!-- /.smallstat -->
        </div> <!-- /.col-md-3 -->

        <div class="col-sm-6 col-md-3">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual green">
                        <div class="statbox-sparkline">20,30,30,29,22,15,20,30,32</div>
                    </div>
                    <div class="title">Feedbacks</div>
                    <div class="value">714</div>
                    <a class="more" href="javascript:void(0);">View More <i class="pull-right icon-angle-right"></i></a>
                </div>
            </div> <!-- /.smallstat -->
        </div> <!-- /.col-md-3 -->

        <div class="col-sm-6 col-md-3 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual yellow">
                        <i class="icon-dollar"></i>
                    </div>
                    <div class="title">Total Profit</div>
                    <div class="value">$42,512.61</div>
                    <a class="more" href="javascript:void(0);">View More <i class="pull-right icon-angle-right"></i></a>
                </div>
            </div> <!-- /.smallstat -->
        </div> <!-- /.col-md-3 -->

        <div class="col-sm-6 col-md-3 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual red">
                        <i class="icon-user"></i>
                    </div>
                    <div class="title">Visitors</div>
                    <div class="value">2 521 719</div>
                    <a class="more" href="javascript:void(0);">View More <i class="pull-right icon-angle-right"></i></a>
                </div>
            </div> <!-- /.smallstat -->
        </div> <!-- /.col-md-3 -->
    </div> <!-- /.row -->
    <!-- /Statboxes -->

    <!--=== Blue Chart ===-->
    <div class="row">
        <div class="col-md-12">
            <div class="widget box">
                <div class="widget-header">
                    <h4><i class="icon-reorder"></i> Total Clicks (<span class="blue">+29%</span>)</h4>
                    <div class="toolbar no-padding">
                        <div class="btn-group">
                            <span class="btn btn-xs widget-collapse"><i class="icon-angle-down"></i></span>
                        </div>
                    </div>
                </div>
                <div class="widget-content">
                    <div id="chart_filled_blue" class="chart"></div>
                </div>
                <div class="divider"></div>
                <div class="widget-content">
                    <ul class="stats"> <!-- .no-dividers -->
                        <li>
                            <strong>4,853</strong>
                            <small>Total Views</small>
                        </li>
                        <li class="light hidden-xs">
                            <strong>271</strong>
                            <small>Last 24 Hours</small>
                        </li>
                        <li>
                            <strong>1,025</strong>
                            <small>Unique Users</small>
                        </li>
                        <li class="light hidden-xs">
                            <strong>105</strong>
                            <small>Last 24 Hours</small>
                        </li>
                    </ul>
                </div>
            </div>
        </div> <!-- /.col-md-12 -->
    </div> <!-- /.row -->
    <!-- /Blue Chart -->

    <div class="row">
        <!--=== Sin/Cos Chart ===-->
        <div class="col-md-6">
            <div class="widget box">
                <div class="widget-header">
                    <h4><i class="icon-reorder"></i> Sine/ Cosine</h4>
                    <div class="toolbar no-padding">
                        <div class="btn-group">
                            <span class="btn btn-xs widget-collapse"><i class="icon-angle-down"></i></span>
                        </div>
                    </div>
                </div>
                <div class="widget-content">
                    <div id="chart_simple" class="chart" style="height: 219px;"></div>
                </div>
            </div>
        </div> <!-- /.col-md-6 -->
        <!-- /Sin/Cos Chart -->

        <!--=== Static Table ===-->
        <div class="col-md-6">
            <div class="widget box">
                <div class="widget-header">
                    <h4><i class="icon-reorder"></i> New Users</h4>
                    <div class="toolbar no-padding">
                        <div class="btn-group">
                            <span class="btn btn-xs widget-collapse"><i class="icon-angle-down"></i></span>
                        </div>
                    </div>
                </div>
                <div class="widget-content no-padding">
                    <table class="table table-striped table-checkable table-hover">
                        <thead>
                        <tr>
                            <th class="checkbox-column">
                                <input type="checkbox" class="uniform">
                            </th>
                            <th class="hidden-xs">First Name</th>
                            <th>Last Name</th>
                            <th>Status</th>
                            <th class="align-center">Approve</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="checkbox-column">
                                <input type="checkbox" class="uniform">
                            </td>
                            <td class="hidden-xs">Joey</td>
                            <td>Greyson</td>
                            <td><span class="label label-success">Approved</span></td>
                            <td class="align-center">
												<span class="btn-group">
													<a href="javascript:void(0);" title="Approve" class="btn btn-xs bs-tooltip"><i class="icon-ok"></i></a>
												</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="checkbox-column">
                                <input type="checkbox" class="uniform">
                            </td>
                            <td class="hidden-xs">Wolf</td>
                            <td>Bud</td>
                            <td><span class="label label-info">Pending</span></td>
                            <td class="align-center">
												<span class="btn-group">
													<a href="javascript:void(0);" title="Approve" class="btn btn-xs bs-tooltip"><i class="icon-ok"></i></a>
												</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="checkbox-column">
                                <input type="checkbox" class="uniform">
                            </td>
                            <td class="hidden-xs">Darin</td>
                            <td>Alec</td>
                            <td><span class="label label-warning">Suspended</span></td>
                            <td class="align-center">
												<span class="btn-group">
													<a href="javascript:void(0);" title="Approve" class="btn btn-xs bs-tooltip"><i class="icon-ok"></i></a>
												</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="checkbox-column">
                                <input type="checkbox" class="uniform">
                            </td>
                            <td class="hidden-xs">Andrea</td>
                            <td>Brenden</td>
                            <td><span class="label label-danger">Blocked</span></td>
                            <td class="align-center">
												<span class="btn-group">
													<a href="javascript:void(0);" title="Approve" class="btn btn-xs bs-tooltip"><i class="icon-ok"></i></a>
												</span>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="row">
                        <div class="table-footer">
                            <div class="col-md-12">
                                <div class="table-actions">
                                    <label>Apply action:</label>
                                    <select class="select2" data-minimum-results-for-search="-1" data-placeholder="Select action...">
                                        <option value=""></option>
                                        <option value="Edit">Edit</option>
                                        <option value="Delete">Delete</option>
                                        <option value="Move">Move</option>
                                    </select>
                                </div>
                            </div>
                        </div> <!-- /.table-footer -->
                    </div> <!-- /.row -->
                </div> <!-- /.widget-content -->
            </div> <!-- /.widget -->
        </div> <!-- /.col-md-6 -->
        <!-- /Static Table -->
    </div> <!-- /.row -->

</template>

<script>
    module.exports = {
        data: function(){
            return {

            }
        },
        ready: function () {
            console.log(this) // -> 'foo'
        },
        methods:{
        },
        components: {
        }
    }
</script>