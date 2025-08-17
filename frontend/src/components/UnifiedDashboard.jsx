import React, { useState, useEffect, useCallback } from 'react';
import { 
  Users, 
  Armchair, 
  Activity,
  Crown,
  User,
  MapPin,
  Clock,
  AlertCircle,
  CheckCircle,
  Target,
  Trash2,
  TrendingUp,
  BarChart3,
  RefreshCw
} from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from './ui/card';
import { Badge } from './ui/badge';
import { Button } from './ui/button';
import { Progress } from './ui/progress';
import { Separator } from './ui/separator';
import { seatApi, employeeApi } from '../services/api';
import WorkspaceStats from './WorkspaceStats';
import SystemStatusPanel from './SystemStatusPanel';
import { 
  checkCapacityWarning, 
  checkManagerSeatsAvailability, 
  checkQueueLimit,
  notifyBusinessRuleViolation 
} from './NotificationCenter';
import toast from 'react-hot-toast';

const UnifiedDashboard = () => {
  const [employees, setEmployees] = useState([]);
  const [seats, setSeats] = useState([]);
  const [unassignedEmployees, setUnassignedEmployees] = useState([]);
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [selectedSeat, setSelectedSeat] = useState(null);
  const [loading, setLoading] = useState(true);
  const [activityLog, setActivityLog] = useState([]);
  const [currentDragData, setCurrentDragData] = useState(null);
  

  const [stats, setStats] = useState({
    totalSeats: 0,
    availableSeats: 0,
    managerSeats: 0,
    queueSize: 0,
    totalEmployees: 0,
    assignedEmployees: 0,
    utilizationRate: 0
  });

  useEffect(() => {
    fetchAllData();
    initializeActivityLog();
  }, []);


  const fetchAllData = async (silent = false) => {
    try {
      if (!silent) {
        setLoading(true);
      }
      
      const [
        allSeatsRes,
        availableSeatsRes,
        managerSeatsRes,
        unassignedEmployeesRes,
        allEmployeesRes,
        assignedEmployeesRes,
        occupiedSeatsRes
      ] = await Promise.all([
        seatApi.getAll(),
        seatApi.getAvailable(),
        seatApi.getManagerSeats(),
        employeeApi.getUnassigned(),
        employeeApi.getAll(),
        employeeApi.getAssigned(),
        seatApi.getOccupied()
      ]);

      const seatsData = allSeatsRes.data.data;
      const unassignedData = unassignedEmployeesRes.data.data;
      const allEmployeesData = allEmployeesRes.data.data;
      const occupiedSeats = occupiedSeatsRes.data.data.length;
      
      setSeats(seatsData);
      setUnassignedEmployees(unassignedData);
      setEmployees(allEmployeesData);
      
      const newStats = {
        totalSeats: seatsData.length,
        availableSeats: availableSeatsRes.data.data.length,
        managerSeats: managerSeatsRes.data.data.length,
        queueSize: Math.min(unassignedData.length, 10),
        totalEmployees: allEmployeesData.length,
        assignedEmployees: assignedEmployeesRes.data.data.length,
        utilizationRate: seatsData.length > 0 ? Math.round((occupiedSeats / seatsData.length) * 100) : 0
      };
      
      setStats(newStats);

      // Check for system alerts (only if not a silent refresh)
      if (!silent) {
        const availableManagerSeats = managerSeatsRes.data.data.filter(seat => !seat.employee).length;
        
        checkCapacityWarning(newStats.totalSeats, newStats.availableSeats);
        checkManagerSeatsAvailability(availableManagerSeats);
        checkQueueLimit(unassignedData.length);
        
        addToActivityLog('System data refreshed');
      }
    } catch (error) {
      if (!silent) {
        toast.error('Failed to load workspace data');
      }
    } finally {
      if (!silent) {
        setLoading(false);
      }
    }
  };

  const initializeActivityLog = () => {
    setActivityLog([
      {
        id: Date.now(),
        message: 'System initialized',
        timestamp: new Date().toLocaleTimeString(),
        type: 'info'
      }
    ]);
  };

  const addToActivityLog = (message, type = 'info') => {
    const timestamp = new Date();
    setActivityLog(prev => [
      { 
        id: Date.now(), 
        message, 
        timestamp: timestamp.toLocaleTimeString(),
        type
      },
      ...prev.slice(0, 9) // Keep only last 10 entries
    ]);
  };

  const handleEmployeeSelect = (employee) => {
    setSelectedEmployee(selectedEmployee?.employeeId === employee.employeeId ? null : employee);
    addToActivityLog(`Employee Selected: ${employee.name} (${employee.designation?.designation})`, 'selection');
  };

  const handleSeatSelect = (seat) => {
    if (!seat.employee) { // Only select available seats
      setSelectedSeat(selectedSeat?.seatNumber === seat.seatNumber ? null : seat);
      addToActivityLog(`Seat Selected: ${seat.seatNumber} (${seat.isManagerSeat ? 'Manager' : 'Regular'})`, 'selection');
    }
  };

  const handleAssignSeat = async () => {
    if (!selectedEmployee || !selectedSeat) {
      toast.error('Please select both an employee and an available seat');
      return;
    }

    try {
      await seatApi.assign({
        seatNumber: selectedSeat.seatNumber,
        employeeId: selectedEmployee.employeeId
      });
      
      toast.success('Seat assigned successfully');
      addToActivityLog(`Assignment: ${selectedEmployee.name} → ${selectedSeat.seatNumber}`, 'assignment');
      
      setSelectedEmployee(null);
      setSelectedSeat(null);
      fetchAllData(true);
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to assign seat');
      addToActivityLog(`Failed to assign seat: ${error.response?.data?.message || 'Unknown error'}`, 'error');
    }
  };

  const handleUnassignSeat = async (seatNumber) => {
    try {
      await seatApi.unassign(seatNumber);
      toast.success('Seat unassigned successfully');
      addToActivityLog(`Unassignment: Seat ${seatNumber} freed`, 'unassignment');
      fetchAllData(true);
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to unassign seat');
    }
  };


  // Enhanced drag and drop handlers with visual feedback
  const handleDragStart = (e, employee, sourceType = 'queue') => {
    const dragData = {
      ...employee,
      sourceType: sourceType, // 'queue' or 'seat'
      sourceLocation: sourceType === 'seat' ? employee.seatNumber : 'queue'
    };
    
    // Store drag data in both dataTransfer and state (for cross-browser compatibility)
    e.dataTransfer.setData('text/plain', JSON.stringify(dragData));
    setCurrentDragData(dragData);
    
    e.currentTarget.classList.add('dragging');
    
    // Add source-specific visual feedback
    if (sourceType === 'queue') {
      e.currentTarget.classList.add('dragging-from-queue');
    } else {
      // For employees being dragged from seats
      e.currentTarget.classList.add('employee-dragging');
    }
    
    // Set drag image for better visual feedback
    e.dataTransfer.effectAllowed = 'move';
    
    addToActivityLog(`Drag Started: ${employee.name} from ${sourceType}`, 'drag_start');
  };

  const handleDragEnd = (e) => {
    e.currentTarget.classList.remove('dragging', 'dragging-from-queue', 'employee-dragging');
    
    // Clear stored drag data
    setCurrentDragData(null);
    
    // Remove any lingering drag styles from all seats
    document.querySelectorAll('.seat-card').forEach(seat => {
      seat.classList.remove('drag-over', 'drag-invalid', 'drag-assign', 'drag-reassign', 'drag-swap', 'drag-forbidden');
    });
    
    // Remove drag styles from queue items and employee info
    document.querySelectorAll('.employee-queue-item').forEach(item => {
      item.classList.remove('dragging-from-queue');
    });
    
    document.querySelectorAll('.employee-dragging').forEach(item => {
      item.classList.remove('employee-dragging');
    });
  };

  const handleDragOver = (e) => {
    e.preventDefault();
    e.dataTransfer.dropEffect = 'move';
  };

  const handleDragEnter = (e, seat) => {
    e.preventDefault();
    
    // Use stored drag data instead of dataTransfer (which is empty during dragenter)
    if (!currentDragData) {
      return; // No drag in progress
    }
    
    const dragData = currentDragData;
    const isManagerEmployee = dragData.designation?.isManager;
    
    // Remove any existing drag classes
    e.currentTarget.classList.remove('drag-over', 'drag-invalid', 'drag-assign', 'drag-reassign', 'drag-swap', 'drag-forbidden');
    
    // Handle different drag scenarios with specific visual feedback
    if (seat.employee) {
      // Target is occupied seat - check if we can swap employees
      if (dragData.sourceType === 'seat') {
        // Employee-to-employee swap: check if both can swap seats
        const targetEmployee = seat.employee;
        const targetIsManager = targetEmployee.designation?.isManager;
        
        // Check if dragged employee can go to target seat
        const canDraggedGoToTarget = isManagerEmployee === seat.isManagerSeat;
        // Check if target employee can go to dragged employee's seat
        const draggedSeat = seats.find(s => s.employee?.employeeId === dragData.employeeId);
        const canTargetGoToSource = draggedSeat ? (targetIsManager === draggedSeat.isManagerSeat) : false;
        
        if (canDraggedGoToTarget && canTargetGoToSource) {
          e.currentTarget.classList.add('drag-swap');
        } else {
          e.currentTarget.classList.add('drag-forbidden');
        }
      } else {
        // Queue employee to occupied seat - not allowed
        e.currentTarget.classList.add('drag-forbidden');
      }
    } else {
      // Target is available seat - check assignment or reassignment
      const canAssign = isManagerEmployee === seat.isManagerSeat;
      
      if (canAssign) {
        if (dragData.sourceType === 'queue') {
          e.currentTarget.classList.add('drag-assign');
        } else {
          e.currentTarget.classList.add('drag-reassign');
        }
      } else {
        e.currentTarget.classList.add('drag-forbidden');
      }
    }
  };

  const handleDragLeave = (e) => {
    e.currentTarget.classList.remove('drag-over', 'drag-invalid', 'drag-assign', 'drag-reassign', 'drag-swap', 'drag-forbidden');
  };

  const handleDrop = async (e, seat) => {
    e.preventDefault();
    e.currentTarget.classList.remove('drag-over', 'drag-invalid', 'drag-assign', 'drag-reassign', 'drag-swap', 'drag-forbidden');
    
    try {
      const dragData = JSON.parse(e.dataTransfer.getData('text/plain'));
      
      // Route operation based on source and target types
      if (seat.employee) {
        // Target is occupied seat - perform employee swap
        if (dragData.sourceType === 'seat') {
          await handleEmployeeSwap(dragData, seat.employee);
        } else {
          toast.error('Cannot assign queue employee to occupied seat');
          return;
        }
      } else {
        // Target is available seat - perform assignment or reassignment
        if (dragData.sourceType === 'queue') {
          await handleEmployeeAssignment(dragData, seat);
        } else {
          await handleEmployeeReassignment(dragData, seat);
        }
      }
      
      // Optimized update: silent refresh to avoid loading spinner
      fetchAllData(true);
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to complete operation');
      addToActivityLog(`Operation Failed: ${error.response?.data?.message || 'Unknown error'}`, 'error');
    }
  };

  // Handle employee assignment from queue to available seat
  const handleEmployeeAssignment = async (employeeData, seat) => {
    // Validate assignment before API call
    const isManagerEmployee = employeeData.designation?.isManager;
    const canAssign = isManagerEmployee === seat.isManagerSeat;
    
    if (!canAssign) {
      const message = isManagerEmployee 
        ? 'Manager employees require manager seats'
        : 'Regular employees cannot be assigned to manager seats';
      toast.error(message);
      
      // Notify about business rule violation
      notifyBusinessRuleViolation('seat_type_mismatch', 
        `Assignment blocked: ${employeeData.name} (${isManagerEmployee ? 'Manager' : 'Regular'}) cannot be assigned to ${seat.isManagerSeat ? 'manager' : 'regular'} seat ${seat.seatNumber}`);
      return;
    }
    
    await seatApi.assign({
      seatNumber: seat.seatNumber,
      employeeId: employeeData.employeeId
    });
    
    toast.success('Employee assigned successfully');
    addToActivityLog(`Assignment: ${employeeData.name} → ${seat.seatNumber}`, 'assignment');
  };

  // Handle employee reassignment from occupied seat to available seat
  const handleEmployeeReassignment = async (employeeData, seat) => {
    // Validate reassignment before API call
    const isManagerEmployee = employeeData.designation?.isManager;
    const canReassign = isManagerEmployee === seat.isManagerSeat;
    
    if (!canReassign) {
      const message = isManagerEmployee 
        ? 'Manager employees require manager seats'
        : 'Regular employees cannot be assigned to manager seats';
      toast.error(message);
      return;
    }
    
    await seatApi.reassignEmployee({
      employeeId: employeeData.employeeId,
      toSeat: seat.seatNumber
    });
    
    toast.success('Employee reassigned successfully');
    addToActivityLog(`Reassignment: ${employeeData.name} → ${seat.seatNumber}`, 'reassignment');
  };

  // Handle employee swap between two occupied seats
  const handleEmployeeSwap = async (draggedEmployee, targetEmployee) => {
    await seatApi.swapEmployees({
      employee1: draggedEmployee.employeeId,
      employee2: targetEmployee.employeeId
    });
    
    toast.success('Employees swapped successfully');
    addToActivityLog(`Swap: ${draggedEmployee.name} ↔ ${targetEmployee.name}`, 'swap');
  };

  const getSeatClassName = (seat) => {
    let classes = ['seat-card'];
    
    // Base seat type (manager seats always get special styling)
    if (seat.isManagerSeat) {
      classes.push('seat-manager');
    }
    
    // Occupancy status
    if (seat.employee) {
      classes.push('seat-occupied');
    } else {
      classes.push('seat-available');
      
      // Assignable highlighting when employee is selected
      if (selectedEmployee) {
        const isManagerEmployee = selectedEmployee.designation?.isManager;
        const canAssign = isManagerEmployee === seat.isManagerSeat;
        
        if (canAssign) {
          classes.push('seat-assignable');
        }
      }
    }
    
    // Selection state
    if (selectedSeat?.seatNumber === seat.seatNumber) {
      classes.push('seat-selected');
    }
    
    return classes.join(' ');
  };

  const businessRules = [
    { icon: AlertCircle, text: 'Manager seats only for managers', type: 'critical' },
    { icon: Users, text: 'Queue limit: 10 employees', type: 'warning' },
    { icon: Armchair, text: 'Max: 100 seats, 5 manager seats', type: 'info' }
  ];

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-[400px]">
        <div className="loading-spinner h-12 w-12"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen glassmorphism-bg p-6">
      <div className="container mx-auto space-y-6">
        {/* Header */}
        <div className="text-center space-y-4">
          <div className="flex items-center justify-center space-x-3">
            <Activity className="h-8 w-8 text-primary" />
            <h1 className="text-4xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
              Interactive Workspace Dashboard
            </h1>
          </div>
          <p className="text-lg text-muted-foreground">
            Complete workspace management with real-time assignment and monitoring
          </p>
        </div>

        {/* Enhanced Statistics */}
        <WorkspaceStats />

        {/* System Status */}
        <SystemStatusPanel />

        {/* Main Dashboard Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-4 gap-6">
          {/* Left Panel - Employee Queue */}
          <div className="lg:col-span-1">
            <Card className="glass-card employee-queue-panel">
              <CardHeader>
                <div className="flex items-center justify-between">
                  <CardTitle className="flex items-center space-x-2">
                    <Users className="h-5 w-5" />
                    <span>Employee Queue</span>
                  </CardTitle>
                  <Badge variant="outline">{stats.queueSize}/10</Badge>
                </div>
                <CardDescription>Drag employees to assign seats</CardDescription>
              </CardHeader>
              <CardContent className="space-y-4">
                {unassignedEmployees.length === 0 ? (
                  <div className="text-center py-8 text-muted-foreground">
                    <Users className="h-12 w-12 mx-auto mb-3 opacity-50" />
                    <p>No employees in queue</p>
                  </div>
                ) : (
                  <div className="space-y-2 custom-scrollbar max-h-[400px] overflow-y-auto">
                    {unassignedEmployees.slice(0, 10).map((employee) => (
                      <div
                        key={employee.employeeId}
                        className={`employee-queue-item ${
                          employee.designation?.isManager ? 'manager' : 'regular'
                        } ${
                          selectedEmployee?.employeeId === employee.employeeId ? 'selected' : ''
                        }`}
                        draggable
                        onDragStart={(e) => handleDragStart(e, employee)}
                        onDragEnd={handleDragEnd}
                        onClick={() => handleEmployeeSelect(employee)}
                      >
                        <div className="flex items-center space-x-3">
                          <div className="p-2 rounded-full bg-primary/10">
                            {employee.designation?.isManager ? (
                              <Crown className="h-4 w-4 text-purple-600" />
                            ) : (
                              <User className="h-4 w-4 text-blue-600" />
                            )}
                          </div>
                          <div className="flex-1 min-w-0">
                            <p className="font-medium truncate">{employee.name}</p>
                            <p className="text-xs text-muted-foreground">{employee.employeeId}</p>
                            <Badge 
                              variant={employee.designation?.isManager ? "default" : "secondary"} 
                              className="text-xs mt-1"
                            >
                              {employee.designation?.designation}
                            </Badge>
                          </div>
                        </div>
                      </div>
                    ))}
                  </div>
                )}
              </CardContent>
            </Card>
          </div>

          {/* Center Panel - Seat Grid */}
          <div className="lg:col-span-2">
            <Card className="glass-card">
              <CardHeader>
                <div className="flex items-center justify-between">
                  <CardTitle className="flex items-center space-x-2">
                    <Armchair className="h-5 w-5" />
                    <span>Seat Grid</span>
                  </CardTitle>
                  <div className="flex items-center space-x-4 text-sm">
                    <span>Total: <strong>{stats.totalSeats}</strong></span>
                    <span>Available: <strong>{stats.availableSeats}</strong></span>
                    <span>Manager: <strong>{stats.managerSeats}</strong></span>
                  </div>
                </div>
                <CardDescription>
                  Click seats to select, drag employees to assign
                </CardDescription>
              </CardHeader>
              <CardContent>
                {seats.length === 0 ? (
                  <div className="text-center py-16 text-muted-foreground">
                    <Armchair className="h-16 w-16 mx-auto mb-4 opacity-50" />
                    <p className="text-lg font-medium mb-2">No seats available</p>
                    <p>Create seats to start managing assignments</p>
                  </div>
                ) : (
                  <div className="seat-grid">
                    {seats.map((seat) => (
                      <div
                        key={seat.seatNumber}
                        className={getSeatClassName(seat)}
                        onClick={() => handleSeatSelect(seat)}
                        onDragOver={handleDragOver}
                        onDragEnter={(e) => handleDragEnter(e, seat)}
                        onDragLeave={handleDragLeave}
                        onDrop={(e) => handleDrop(e, seat)}
                      >
                        <div className="flex items-center justify-between mb-2">
                          <div className="flex items-center space-x-2">
                            <Armchair className="h-4 w-4" />
                            {seat.isManagerSeat && <Crown className="h-3 w-3 text-purple-600" />}
                          </div>
                          {seat.employee && (
                            <Button
                              variant="ghost"
                              size="sm"
                              className="h-6 w-6 p-0"
                              onClick={(e) => {
                                e.stopPropagation();
                                handleUnassignSeat(seat.seatNumber);
                              }}
                            >
                              <Trash2 className="h-3 w-3" />
                            </Button>
                          )}
                        </div>
                        
                        <div className="space-y-1">
                          <p className="font-medium text-sm">{seat.seatNumber}</p>
                          <p className="text-xs text-muted-foreground">Ext: {seat.extension}</p>
                          
                          {seat.employee ? (
                            <div 
                              className="mt-2 p-2 bg-white/50 rounded cursor-move hover:bg-white/70 transition-colors"
                              draggable
                              onDragStart={(e) => handleDragStart(e, seat.employee, 'seat')}
                              onDragEnd={handleDragEnd}
                              onClick={(e) => e.stopPropagation()}
                            >
                              <p className="text-xs font-medium">{seat.employee.name}</p>
                              <p className="text-xs text-muted-foreground">{seat.employee.employeeId}</p>
                            </div>
                          ) : (
                            <Badge variant="outline" className="text-xs mt-2">
                              {seat.isManagerSeat ? 'Manager' : 'Regular'}
                            </Badge>
                          )}
                        </div>
                      </div>
                    ))}
                  </div>
                )}
              </CardContent>
            </Card>
          </div>

          {/* Right Panel - Assignment & Info */}
          <div className="lg:col-span-1 space-y-6">
            {/* Assignment Panel */}
            <Card className="glass-card">
              <CardHeader>
                <CardTitle className="flex items-center space-x-2">
                  <Target className="h-5 w-5" />
                  <span>Assignment</span>
                </CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                {selectedEmployee || selectedSeat ? (
                  <div className="space-y-4">
                    {selectedEmployee && (
                      <div className="p-3 rounded-lg bg-blue-50 border border-blue-200">
                        <p className="text-sm font-medium text-blue-800">Selected Employee</p>
                        <p className="font-medium">{selectedEmployee.name}</p>
                        <Badge variant={selectedEmployee.designation?.isManager ? "default" : "secondary"} className="text-xs mt-1">
                          {selectedEmployee.designation?.designation}
                        </Badge>
                      </div>
                    )}
                    
                    {selectedSeat && (
                      <div className="p-3 rounded-lg bg-green-50 border border-green-200">
                        <p className="text-sm font-medium text-green-800">Selected Seat</p>
                        <p className="font-medium">{selectedSeat.seatNumber}</p>
                        <Badge variant={selectedSeat.isManagerSeat ? "default" : "secondary"} className="text-xs mt-1">
                          {selectedSeat.isManagerSeat ? 'Manager Seat' : 'Regular Seat'}
                        </Badge>
                      </div>
                    )}
                    
                    {selectedEmployee && selectedSeat && (
                      <Button onClick={handleAssignSeat} className="w-full">
                        <CheckCircle className="h-4 w-4 mr-2" />
                        Assign Seat
                      </Button>
                    )}
                  </div>
                ) : (
                  <div className="text-center py-4 text-muted-foreground">
                    <Target className="h-8 w-8 mx-auto mb-2 opacity-50" />
                    <p className="text-sm">Select employee and seat to assign</p>
                  </div>
                )}
              </CardContent>
            </Card>

            {/* Business Rules */}
            <Card className="glass-card">
              <CardHeader>
                <CardTitle className="flex items-center space-x-2 text-sm">
                  <AlertCircle className="h-4 w-4" />
                  <span>Active Rules</span>
                </CardTitle>
              </CardHeader>
              <CardContent className="space-y-3">
                {businessRules.map((rule, index) => {
                  const Icon = rule.icon;
                  return (
                    <div key={index} className="flex items-start space-x-2 text-sm">
                      <Icon className="h-4 w-4 mt-0.5 text-muted-foreground" />
                      <span>{rule.text}</span>
                    </div>
                  );
                })}
              </CardContent>
            </Card>

            {/* Activity Log */}
            <Card className="glass-card">
              <CardHeader>
                <CardTitle className="flex items-center space-x-2 text-sm">
                  <Clock className="h-4 w-4" />
                  <span>Recent Activity</span>
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-2 custom-scrollbar max-h-[200px] overflow-y-auto">
                  {activityLog.length === 0 ? (
                    <p className="text-sm text-muted-foreground text-center py-4">No recent activity</p>
                  ) : (
                    activityLog.map((entry) => (
                      <div key={entry.id} className="log-entry">
                        <div className="flex items-start justify-between">
                          <p className="text-xs flex-1">{entry.message}</p>
                          <span className="text-xs text-muted-foreground ml-2">{entry.timestamp}</span>
                        </div>
                      </div>
                    ))
                  )}
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UnifiedDashboard;