import React, { useState } from 'react';
import { Armchair, Crown, User, X, Plus, AlertTriangle } from 'lucide-react';
import clsx from 'clsx';

const SeatGrid = ({ seats, unassignedEmployees, selectedEmployee, onAssignSeat, onUnassignSeat }) => {
  const [selectedSeat, setSelectedSeat] = useState(null);
  const [showAssignModal, setShowAssignModal] = useState(false);

  const handleSeatClick = (seat) => {
    setSelectedSeat(seat);
    if (!seat.employee) {
      if (selectedEmployee) {
        // Direct assignment from queue
        handleAssignEmployee(selectedEmployee.employeeId);
      } else {
        // Fallback to modal (though this shouldn't happen with queue system)
        setShowAssignModal(true);
      }
    }
  };

  const handleAssignEmployee = (employeeId) => {
    if (selectedSeat && employeeId) {
      onAssignSeat(selectedSeat.seatNumber, employeeId);
      setShowAssignModal(false);
      setSelectedSeat(null);
    }
  };

  const handleUnassign = () => {
    if (selectedSeat && selectedSeat.employee) {
      onUnassignSeat(selectedSeat.seatNumber);
      setSelectedSeat(null);
    }
  };

  const canAssignToSeat = (seat) => {
    if (seat.employee || !selectedEmployee) return false;
    
    // Check manager seat requirements
    if (seat.isManagerSeat && !selectedEmployee.designation?.isManager) return false;
    if (!seat.isManagerSeat && selectedEmployee.designation?.isManager) return false;
    
    return true;
  };

  return (
    <div className="space-y-6">
      {/* Selected Employee Info */}
      {selectedEmployee && (
        <div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
          <div className="flex items-center space-x-2 mb-2">
            <User className="h-4 w-4 text-blue-600" />
            <span className="text-sm font-medium text-blue-900">Selected Employee:</span>
          </div>
          <div className="text-sm">
            <div className="font-medium text-blue-900">{selectedEmployee.name}</div>
            <div className="text-blue-700">ID: {selectedEmployee.employeeId}</div>
            <div className="text-blue-700">
              {selectedEmployee.designation?.designation}
              {selectedEmployee.designation?.isManager && ' (Manager)'}
            </div>
          </div>
          <div className="mt-2 text-xs text-blue-700">
            Click on an available {selectedEmployee.designation?.isManager ? 'manager ' : ''}seat to assign this employee
          </div>
        </div>
      )}

      {/* Legend */}
      <div className="flex flex-wrap gap-4 text-sm">
        <div className="flex items-center space-x-2">
          <div className="w-4 h-4 bg-green-100 border-2 border-green-300 rounded"></div>
          <span>Available</span>
        </div>
        <div className="flex items-center space-x-2">
          <div className="w-4 h-4 bg-blue-100 border-2 border-blue-300 rounded"></div>
          <span>Occupied</span>
        </div>
        <div className="flex items-center space-x-2">
          <div className="w-4 h-4 bg-purple-100 border-2 border-purple-300 rounded"></div>
          <span>Manager Seat</span>
        </div>
        {selectedEmployee && (
          <div className="flex items-center space-x-2">
            <div className="w-4 h-4 bg-yellow-100 border-2 border-yellow-300 rounded"></div>
            <span>Assignable to Selected Employee</span>
          </div>
        )}
      </div>

      {/* Seat Grid */}
      <div className="seat-grid">
        {seats.map((seat) => (
          <div
            key={seat.seatNumber}
            className={clsx(
              'seat-card',
              {
                'seat-available': !seat.employee && !seat.isManagerSeat && !canAssignToSeat(seat),
                'seat-occupied': seat.employee && !seat.isManagerSeat,
                'seat-manager': seat.isManagerSeat && !canAssignToSeat(seat),
                'seat-assignable': canAssignToSeat(seat),
                'seat-selected': selectedSeat?.seatNumber === seat.seatNumber,
              }
            )}
            onClick={() => handleSeatClick(seat)}
          >
            <div className="flex items-center justify-between mb-2">
              <div className="flex items-center space-x-1">
                <Armchair className="h-4 w-4" />
                {seat.isManagerSeat && <Crown className="h-3 w-3 text-purple-600" />}
              </div>
              {seat.employee ? (
                <button
                  onClick={(e) => {
                    e.stopPropagation();
                    setSelectedSeat(seat);
                    handleUnassign();
                  }}
                  className="text-red-500 hover:text-red-700"
                  title="Unassign seat"
                >
                  <X className="h-4 w-4" />
                </button>
              ) : (
                <Plus className="h-4 w-4 text-gray-400" />
              )}
            </div>
            
            <div className="text-xs font-medium text-gray-700 mb-1">
              {seat.seatNumber}
            </div>
            
            <div className="text-xs text-gray-600 mb-2">
              Ext: {seat.extensionNumber}
            </div>
            
            {seat.employee ? (
              <div className="flex items-center space-x-1">
                <User className="h-3 w-3 text-gray-500" />
                <div className="text-xs">
                  <div className="font-medium text-gray-800 truncate">
                    {seat.employee.name}
                  </div>
                  <div className="text-gray-600 truncate">
                    {seat.employee.employeeId}
                  </div>
                </div>
              </div>
            ) : (
              <div className="text-xs text-gray-500 italic">Available</div>
            )}
          </div>
        ))}
      </div>

      {/* Assignment Modal */}
      {showAssignModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-6 w-full max-w-md">
            <h3 className="text-lg font-semibold mb-4">
              Assign Employee to {selectedSeat?.seatNumber}
            </h3>
            
            <div className="space-y-2 max-h-60 overflow-y-auto">
              {unassignedEmployees
                .filter(emp => {
                  // Filter employees based on seat type
                  if (selectedSeat?.isManagerSeat) {
                    return emp.designation?.isManager;
                  }
                  return true; // Non-manager seats can accommodate anyone
                })
                .map((employee) => (
                <button
                  key={employee.employeeId}
                  onClick={() => handleAssignEmployee(employee.employeeId)}
                  className="w-full p-3 text-left border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
                >
                  <div className="font-medium text-gray-900">{employee.name}</div>
                  <div className="text-sm text-gray-600">{employee.employeeId}</div>
                  <div className="text-sm text-gray-600">
                    {employee.designation?.designation}
                    {employee.designation?.isManager && (
                      <span className="ml-2 text-purple-600 font-medium">(Manager)</span>
                    )}
                  </div>
                </button>
              ))}
            </div>
            
            {unassignedEmployees.filter(emp => 
              selectedSeat?.isManagerSeat ? emp.designation?.isManager : true
            ).length === 0 && (
              <p className="text-gray-500 text-center py-4">
                {selectedSeat?.isManagerSeat 
                  ? 'No unassigned managers available'
                  : 'No unassigned employees available'
                }
              </p>
            )}
            
            <div className="flex justify-end space-x-3 mt-6">
              <button
                onClick={() => {
                  setShowAssignModal(false);
                  setSelectedSeat(null);
                }}
                className="btn btn-secondary"
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default SeatGrid;