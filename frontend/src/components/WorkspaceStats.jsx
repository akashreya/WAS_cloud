import React, { useState, useEffect } from 'react';
import { 
  Users, 
  Armchair, 
  Crown, 
  Clock, 
  TrendingUp,
  Activity,
  AlertTriangle,
  CheckCircle
} from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from './ui/card';
import { Badge } from './ui/badge';
import { Progress } from './ui/progress';
import { seatApi, employeeApi } from '../services/api';

const WorkspaceStats = () => {
  const [stats, setStats] = useState({
    totalEmployees: 0,
    totalSeats: 0,
    occupiedSeats: 0,
    availableSeats: 0,
    managerSeats: 0,
    managerEmployees: 0,
    queueSize: 0,
    utilizationRate: 0,
    managerSeatUsage: 0,
    recentAssignments: 0
  });
  const [loading, setLoading] = useState(true);
  const [trend, setTrend] = useState({
    direction: 'up',
    percentage: 12.5
  });

  useEffect(() => {
    fetchStats();
    const interval = setInterval(fetchStats, 30000); // Refresh every 30 seconds
    return () => clearInterval(interval);
  }, []);

  const fetchStats = async () => {
    try {
      const [
        allSeatsRes,
        occupiedSeatsRes,
        availableSeatsRes,
        managerSeatsRes,
        allEmployeesRes,
        unassignedEmployeesRes
      ] = await Promise.all([
        seatApi.getAll(),
        seatApi.getOccupied(),
        seatApi.getAvailable(),
        seatApi.getManagerSeats(),
        employeeApi.getAll(),
        employeeApi.getUnassigned()
      ]);

      const totalSeats = allSeatsRes.data.data.length;
      const occupiedSeats = occupiedSeatsRes.data.data.length;
      const availableSeats = availableSeatsRes.data.data.length;
      const managerSeats = managerSeatsRes.data.data.length;
      const totalEmployees = allEmployeesRes.data.data.length;
      const queueSize = unassignedEmployeesRes.data.data.length;
      const managerEmployees = allEmployeesRes.data.data.filter(emp => emp.designation?.isManager).length;

      setStats({
        totalEmployees,
        totalSeats,
        occupiedSeats,
        availableSeats,
        managerSeats,
        managerEmployees,
        queueSize,
        utilizationRate: totalSeats > 0 ? Math.round((occupiedSeats / totalSeats) * 100) : 0,
        managerSeatUsage: managerSeats > 0 ? Math.round((managerSeats / 5) * 100) : 0,
        recentAssignments: occupiedSeats // Simplified for demo
      });
    } catch (error) {
    } finally {
      setLoading(false);
    }
  };

  const getUtilizationStatus = () => {
    if (stats.utilizationRate >= 90) return { color: 'text-red-600', status: 'Critical' };
    if (stats.utilizationRate >= 75) return { color: 'text-yellow-600', status: 'High' };
    if (stats.utilizationRate >= 50) return { color: 'text-blue-600', status: 'Good' };
    return { color: 'text-green-600', status: 'Low' };
  };

  const utilizationStatus = getUtilizationStatus();

  if (loading) {
    return (
      <Card className="glass-card">
        <CardContent className="p-6">
          <div className="flex items-center justify-center">
            <div className="loading-spinner h-8 w-8"></div>
          </div>
        </CardContent>
      </Card>
    );
  }

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      {/* Total Employees */}
      <Card className="glass-card">
        <CardContent className="p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm font-medium text-muted-foreground">Total Employees</p>
              <p className="text-2xl font-bold">{stats.totalEmployees}</p>
              <div className="flex items-center space-x-1 mt-1">
                <Badge variant={stats.queueSize > 5 ? "warning" : "success"} className="text-xs">
                  {stats.queueSize} in queue
                </Badge>
              </div>
            </div>
            <div className="p-3 rounded-full bg-blue-100">
              <Users className="h-6 w-6 text-blue-600" />
            </div>
          </div>
        </CardContent>
      </Card>

      {/* Seat Utilization */}
      <Card className="glass-card">
        <CardContent className="p-6">
          <div className="flex items-center justify-between">
            <div className="flex-1">
              <div className="flex items-center justify-between mb-2">
                <p className="text-sm font-medium text-muted-foreground">Seat Utilization</p>
                <span className={`text-sm font-medium ${utilizationStatus.color}`}>
                  {utilizationStatus.status}
                </span>
              </div>
              <p className="text-2xl font-bold mb-2">{stats.utilizationRate}%</p>
              <Progress value={stats.utilizationRate} className="h-2" />
              <p className="text-xs text-muted-foreground mt-2">
                {stats.occupiedSeats} of {stats.totalSeats} seats occupied
              </p>
            </div>
            <div className="p-3 rounded-full bg-purple-100">
              <Armchair className="h-6 w-6 text-purple-600" />
            </div>
          </div>
        </CardContent>
      </Card>

      {/* Manager Resources */}
      <Card className="glass-card">
        <CardContent className="p-6">
          <div className="flex items-center justify-between">
            <div className="flex-1">
              <p className="text-sm font-medium text-muted-foreground">Manager Resources</p>
              <p className="text-2xl font-bold">{stats.managerEmployees}/5</p>
              <div className="mt-2">
                <div className="flex items-center justify-between text-xs mb-1">
                  <span>Manager Seats</span>
                  <span>{stats.managerSeats}/5</span>
                </div>
                <Progress value={stats.managerSeatUsage} className="h-2" />
              </div>
            </div>
            <div className="p-3 rounded-full bg-yellow-100">
              <Crown className="h-6 w-6 text-yellow-600" />
            </div>
          </div>
        </CardContent>
      </Card>

      {/* System Health */}
      <Card className="glass-card">
        <CardContent className="p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm font-medium text-muted-foreground">System Health</p>
              <div className="flex items-center space-x-2 mt-2">
                <CheckCircle className="h-5 w-5 text-green-500" />
                <span className="text-lg font-semibold text-green-600">Operational</span>
              </div>
              <div className="mt-2 space-y-1">
                <div className="flex items-center justify-between text-xs">
                  <span>Available Seats</span>
                  <span className="font-medium">{stats.availableSeats}</span>
                </div>
                <div className="flex items-center justify-between text-xs">
                  <span>Queue Length</span>
                  <span className="font-medium">{stats.queueSize}/10</span>
                </div>
              </div>
            </div>
            <div className="p-3 rounded-full bg-green-100">
              <Activity className="h-6 w-6 text-green-600" />
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default WorkspaceStats;