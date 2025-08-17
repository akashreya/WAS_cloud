import React, { useState, useRef, useEffect } from "react";
import {
  Building2,
  Users,
  MapPin,
  Phone,
  Monitor,
  Crown,
  User,
  X,
  Plus,
  Search,
  Filter,
  Grid3X3,
  List,
  ZoomIn,
  ZoomOut,
  RotateCcw,
} from "lucide-react";
import clsx from "clsx";

const CorporateSeatPlan = ({
  seats,
  unassignedEmployees,
  selectedEmployee,
  onAssignSeat,
  onUnassignSeat,
  onSeatClick,
}) => {
  const [selectedSeat, setSelectedSeat] = useState(null);
  const [showAssignModal, setShowAssignModal] = useState(false);
  const [viewMode, setViewMode] = useState("grid"); // 'grid' or 'list'
  const [zoom, setZoom] = useState(1);
  const [searchTerm, setSearchTerm] = useState("");
  const [filterType, setFilterType] = useState("all"); // 'all', 'available', 'occupied', 'manager'
  const [showLegend, setShowLegend] = useState(true);
  const containerRef = useRef(null);

  // Filter seats based on search and filter criteria
  const filteredSeats = seats.filter((seat) => {
    const matchesSearch =
      searchTerm === "" ||
      seat.seatNumber.toLowerCase().includes(searchTerm.toLowerCase()) ||
      (seat.employee &&
        seat.employee.name.toLowerCase().includes(searchTerm.toLowerCase()));

    const matchesFilter =
      filterType === "all" ||
      (filterType === "available" && !seat.employee) ||
      (filterType === "occupied" && seat.employee) ||
      (filterType === "manager" && seat.isManagerSeat);

    return matchesSearch && matchesFilter;
  });

  const handleSeatClick = (seat) => {
    setSelectedSeat(seat);
    if (onSeatClick) {
      onSeatClick(seat);
    }

    if (!seat.employee && selectedEmployee) {
      handleAssignEmployee(selectedEmployee.employeeId);
    } else if (!seat.employee) {
      setShowAssignModal(true);
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

    if (seat.isManagerSeat && !selectedEmployee.designation?.isManager)
      return false;
    if (!seat.isManagerSeat && selectedEmployee.designation?.isManager)
      return false;

    return true;
  };

  const getSeatStatus = (seat) => {
    if (seat.employee) return "occupied";
    if (canAssignToSeat(seat)) return "assignable";
    if (seat.isManagerSeat) return "manager";
    return "available";
  };

  const getSeatClass = (seat) => {
    const status = getSeatStatus(seat);
    return clsx("cubicle-seat", `cubicle-seat-${status}`, {
      "cubicle-seat-selected": selectedSeat?.seatNumber === seat.seatNumber,
      "cubicle-seat-hoverable": !seat.employee || canAssignToSeat(seat),
    });
  };

  const handleZoom = (direction) => {
    const newZoom =
      direction === "in" ? Math.min(zoom + 0.1, 2) : Math.max(zoom - 0.1, 0.5);
    setZoom(newZoom);
  };

  const resetZoom = () => {
    setZoom(1);
  };

  // Group seats by floor/section for better organization
  const groupedSeats = filteredSeats.reduce((acc, seat) => {
    const section = seat.section || "Main Floor";
    if (!acc[section]) acc[section] = [];
    acc[section].push(seat);
    return acc;
  }, {});

  return (
    <div className="corporate-seat-plan-container">
      {/* Header Controls */}
      <div className="seat-plan-header">
        <div className="header-left">
          <h2 className="text-2xl font-bold text-gray-900 flex items-center gap-2">
            <Building2 className="h-6 w-6 text-blue-600" />
            Corporate Seat Plan
          </h2>
          <p className="text-gray-600">
            Manage workspace allocation and seating arrangements
          </p>
        </div>

        <div className="header-controls">
          <div className="search-filter-group">
            <div className="search-box">
              <Search className="h-4 w-4 text-gray-400" />
              <input
                type="text"
                placeholder="Search seats or employees..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="search-input"
              />
            </div>

            <select
              value={filterType}
              onChange={(e) => setFilterType(e.target.value)}
              className="filter-select"
            >
              <option value="all">All Seats</option>
              <option value="available">Available</option>
              <option value="occupied">Occupied</option>
              <option value="manager">Manager Seats</option>
            </select>
          </div>

          <div className="view-controls">
            <button
              onClick={() => setViewMode("grid")}
              className={clsx("view-btn", { active: viewMode === "grid" })}
            >
              <Grid3X3 className="h-4 w-4" />
            </button>
            <button
              onClick={() => setViewMode("list")}
              className={clsx("view-btn", { active: viewMode === "list" })}
            >
              <List className="h-4 w-4" />
            </button>
          </div>

          <div className="zoom-controls">
            <button onClick={() => handleZoom("out")} className="zoom-btn">
              <ZoomOut className="h-4 w-4" />
            </button>
            <span className="zoom-level">{Math.round(zoom * 100)}%</span>
            <button onClick={() => handleZoom("in")} className="zoom-btn">
              <ZoomIn className="h-4 w-4" />
            </button>
            <button onClick={resetZoom} className="zoom-btn">
              <RotateCcw className="h-4 w-4" />
            </button>
          </div>
        </div>
      </div>

      {/* Selected Employee Info */}
      {selectedEmployee && (
        <div className="selected-employee-banner">
          <div className="flex items-center space-x-3">
            <div className="employee-avatar">
              <User className="h-5 w-5 text-white" />
            </div>
            <div className="employee-info">
              <div className="employee-name">{selectedEmployee.name}</div>
              <div className="employee-details">
                ID: {selectedEmployee.employeeId} •{" "}
                {selectedEmployee.designation?.designation}
                {selectedEmployee.designation?.isManager && (
                  <span className="manager-badge">Manager</span>
                )}
              </div>
            </div>
          </div>
          <div className="assignment-instructions">
            Click on an available{" "}
            {selectedEmployee.designation?.isManager ? "manager " : ""}seat to
            assign this employee
          </div>
        </div>
      )}

      {/* Legend */}
      {showLegend && (
        <div className="seat-legend">
          <div className="legend-item">
            <div className="legend-color available"></div>
            <span>Available</span>
          </div>
          <div className="legend-item">
            <div className="legend-color occupied"></div>
            <span>Occupied</span>
          </div>
          <div className="legend-item">
            <div className="legend-color manager"></div>
            <span>Manager Seat</span>
          </div>
          <div className="legend-item">
            <div className="legend-color assignable"></div>
            <span>Assignable</span>
          </div>
          <div className="legend-item">
            <div className="legend-color meeting-room"></div>
            <span>Meeting Room</span>
          </div>
          <div className="legend-item">
            <div className="legend-color common-area"></div>
            <span>Common Area</span>
          </div>
        </div>
      )}

      {/* Main Seat Plan */}
      <div className="seat-plan-main" ref={containerRef}>
        <div
          className="seat-plan-content"
          style={{ transform: `scale(${zoom})` }}
        >
          {viewMode === "grid" ? (
            // Grid View - Corporate Layout
            <div className="corporate-layout">
              {/* Meeting Rooms Section */}
              <div className="meeting-rooms-section">
                <h3 className="section-title">Meeting Rooms</h3>
                <div className="meeting-rooms-grid">
                  <div className="meeting-room large">
                    <div className="room-label">Conference Room A</div>
                    <div className="room-capacity">Capacity: 12</div>
                  </div>
                  <div className="meeting-room medium">
                    <div className="room-label">Meeting Room B</div>
                    <div className="room-capacity">Capacity: 6</div>
                  </div>
                  <div className="meeting-room small">
                    <div className="room-label">Huddle Space</div>
                    <div className="room-capacity">Capacity: 4</div>
                  </div>
                </div>
              </div>

              {/* Main Workspace */}
              <div className="main-workspace">
                <h3 className="section-title">Main Workspace</h3>

                {/* Cubicle Rows */}
                {Object.entries(groupedSeats).map(([section, sectionSeats]) => (
                  <div key={section} className="cubicle-section">
                    <h4 className="subsection-title">{section}</h4>
                    <div className="cubicle-rows">
                      {Array.from(
                        { length: Math.ceil(sectionSeats.length / 4) },
                        (_, rowIndex) => (
                          <div key={rowIndex} className="cubicle-row">
                            {sectionSeats
                              .slice(rowIndex * 4, (rowIndex + 1) * 4)
                              .map((seat) => (
                                <div
                                  key={seat.seatNumber}
                                  className={getSeatClass(seat)}
                                  onClick={() => handleSeatClick(seat)}
                                >
                                  {/* Cubicle Structure */}
                                  <div className="cubicle-walls">
                                    <div className="wall wall-top"></div>
                                    <div className="wall wall-left"></div>
                                    <div className="wall wall-right"></div>
                                    <div className="wall wall-bottom"></div>
                                  </div>

                                  {/* Seat Content */}
                                  <div className="seat-content">
                                    <div className="seat-header">
                                      <div className="seat-number">
                                        {seat.seatNumber}
                                      </div>
                                      {seat.isManagerSeat && (
                                        <Crown className="h-4 w-4 text-purple-600" />
                                      )}
                                      {seat.employee ? (
                                        <button
                                          onClick={(e) => {
                                            e.stopPropagation();
                                            setSelectedSeat(seat);
                                            handleUnassign();
                                          }}
                                          className="unassign-btn"
                                          title="Unassign seat"
                                        >
                                          <X className="h-3 w-3" />
                                        </button>
                                      ) : (
                                        <Plus className="h-4 w-4 text-gray-400" />
                                      )}
                                    </div>

                                    <div className="seat-details">
                                      <div className="extension-info">
                                        <Phone className="h-3 w-3 text-gray-500" />
                                        <span>{seat.extensionNumber}</span>
                                      </div>

                                      {seat.employee ? (
                                        <div className="employee-info">
                                          <div className="employee-name">
                                            {seat.employee.name}
                                          </div>
                                          <div className="employee-id">
                                            {seat.employee.employeeId}
                                          </div>
                                          <div className="employee-designation">
                                            {
                                              seat.employee.designation
                                                ?.designation
                                            }
                                          </div>
                                        </div>
                                      ) : (
                                        <div className="available-label">
                                          Available
                                        </div>
                                      )}
                                    </div>
                                  </div>
                                </div>
                              ))}
                          </div>
                        )
                      )}
                    </div>
                  </div>
                ))}
              </div>

              {/* Common Areas */}
              <div className="common-areas-section">
                <h3 className="section-title">Common Areas</h3>
                <div className="common-areas-grid">
                  <div className="common-area break-room">
                    <div className="area-label">Break Room</div>
                  </div>
                  <div className="common-area kitchen">
                    <div className="area-label">Kitchen</div>
                  </div>
                  <div className="common-area reception">
                    <div className="area-label">Reception</div>
                  </div>
                </div>
              </div>
            </div>
          ) : (
            // List View
            <div className="seat-list-view">
              <div className="list-header">
                <div className="list-col">Seat #</div>
                <div className="list-col">Extension</div>
                <div className="list-col">Status</div>
                <div className="list-col">Employee</div>
                <div className="list-col">Type</div>
                <div className="list-col">Actions</div>
              </div>
              {filteredSeats.map((seat) => (
                <div key={seat.seatNumber} className="seat-list-item">
                  <div className="list-col">{seat.seatNumber}</div>
                  <div className="list-col">{seat.extensionNumber}</div>
                  <div className="list-col">
                    <span className={`status-badge ${getSeatStatus(seat)}`}>
                      {getSeatStatus(seat)}
                    </span>
                  </div>
                  <div className="list-col">
                    {seat.employee ? seat.employee.name : "Available"}
                  </div>
                  <div className="list-col">
                    {seat.isManagerSeat ? "Manager" : "Regular"}
                  </div>
                  <div className="list-col">
                    <button
                      onClick={() => handleSeatClick(seat)}
                      className="action-btn"
                    >
                      {seat.employee ? "View" : "Assign"}
                    </button>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      {/* Assignment Modal */}
      {showAssignModal && (
        <div className="modal-overlay">
          <div className="modal-content">
            <div className="modal-header">
              <h3 className="modal-title">
                Assign Employee to {selectedSeat?.seatNumber}
              </h3>
              <button
                onClick={() => {
                  setShowAssignModal(false);
                  setSelectedSeat(null);
                }}
                className="modal-close"
              >
                <X className="h-5 w-5" />
              </button>
            </div>

            <div className="modal-body">
              <div className="employee-list">
                {unassignedEmployees
                  .filter((emp) => {
                    if (selectedSeat?.isManagerSeat) {
                      return emp.designation?.isManager;
                    }
                    return true;
                  })
                  .map((employee) => (
                    <button
                      key={employee.employeeId}
                      onClick={() => handleAssignEmployee(employee.employeeId)}
                      className="employee-option"
                    >
                      <div className="employee-option-info">
                        <div className="employee-option-name">
                          {employee.name}
                        </div>
                        <div className="employee-option-details">
                          ID: {employee.employeeId} •{" "}
                          {employee.designation?.designation}
                          {employee.designation?.isManager && (
                            <span className="manager-indicator">(Manager)</span>
                          )}
                        </div>
                      </div>
                    </button>
                  ))}
              </div>

              {unassignedEmployees.filter((emp) =>
                selectedSeat?.isManagerSeat ? emp.designation?.isManager : true
              ).length === 0 && (
                <p className="no-employees-message">
                  {selectedSeat?.isManagerSeat
                    ? "No unassigned managers available"
                    : "No unassigned employees available"}
                </p>
              )}
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default CorporateSeatPlan;
