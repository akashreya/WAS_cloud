import React, { useState, useEffect, useRef } from 'react';
import { 
  Search, 
  Filter, 
  X, 
  User, 
  Crown, 
  Armchair,
  MapPin,
  Clock
} from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from './ui/card';
import { Badge } from './ui/badge';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Label } from './ui/label';
import { employeeApi, seatApi } from '../services/api';

const AdvancedSearch = ({ onResultSelect, searchType = 'all' }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const [filters, setFilters] = useState({
    type: 'all', // 'employees', 'seats', 'all'
    status: 'all', // 'assigned', 'unassigned', 'available', 'occupied'
    designation: 'all', // 'manager', 'regular'
    seatType: 'all' // 'manager', 'regular'
  });
  const [results, setResults] = useState({
    employees: [],
    seats: []
  });
  const [loading, setLoading] = useState(false);
  const [recentSearches, setRecentSearches] = useState([]);
  const searchRef = useRef(null);

  useEffect(() => {
    const recent = JSON.parse(localStorage.getItem('recentSearches') || '[]');
    setRecentSearches(recent.slice(0, 5));
  }, []);

  useEffect(() => {
    if (searchQuery.length >= 2) {
      performSearch();
    } else {
      setResults({ employees: [], seats: [] });
    }
  }, [searchQuery, filters]);

  const performSearch = async () => {
    setLoading(true);
    try {
      const promises = [];
      
      if (filters.type === 'all' || filters.type === 'employees') {
        promises.push(searchEmployees());
      }
      
      if (filters.type === 'all' || filters.type === 'seats') {
        promises.push(searchSeats());
      }

      await Promise.all(promises);
      
      // Save to recent searches
      const newSearch = {
        query: searchQuery,
        filters: { ...filters },
        timestamp: Date.now()
      };
      
      const updatedRecent = [
        newSearch,
        ...recentSearches.filter(s => s.query !== searchQuery)
      ].slice(0, 5);
      
      setRecentSearches(updatedRecent);
      localStorage.setItem('recentSearches', JSON.stringify(updatedRecent));
    } catch (error) {
    } finally {
      setLoading(false);
    }
  };

  const searchEmployees = async () => {
    try {
      let employeeResults = [];
      
      if (searchQuery) {
        const searchRes = await employeeApi.search(searchQuery);
        employeeResults = searchRes.data.data;
      } else {
        const allRes = await employeeApi.getAll();
        employeeResults = allRes.data.data;
      }

      // Apply filters
      if (filters.status !== 'all') {
        if (filters.status === 'assigned') {
          const assignedRes = await employeeApi.getAssigned();
          const assignedIds = new Set(assignedRes.data.data.map(e => e.employeeId));
          employeeResults = employeeResults.filter(e => assignedIds.has(e.employeeId));
        } else if (filters.status === 'unassigned') {
          const unassignedRes = await employeeApi.getUnassigned();
          const unassignedIds = new Set(unassignedRes.data.data.map(e => e.employeeId));
          employeeResults = employeeResults.filter(e => unassignedIds.has(e.employeeId));
        }
      }

      if (filters.designation !== 'all') {
        employeeResults = employeeResults.filter(e => {
          const isManager = e.designation?.isManager;
          return filters.designation === 'manager' ? isManager : !isManager;
        });
      }

      setResults(prev => ({ ...prev, employees: employeeResults }));
    } catch (error) {
    }
  };

  const searchSeats = async () => {
    try {
      let seatResults = [];
      
      const allRes = await seatApi.getAll();
      seatResults = allRes.data.data;

      // Filter by search query (seat number or extension)
      if (searchQuery) {
        seatResults = seatResults.filter(seat => 
          seat.seatNumber.toLowerCase().includes(searchQuery.toLowerCase()) ||
          seat.extension.includes(searchQuery)
        );
      }

      // Apply status filter
      if (filters.status !== 'all') {
        if (filters.status === 'occupied') {
          seatResults = seatResults.filter(s => s.employee);
        } else if (filters.status === 'available') {
          seatResults = seatResults.filter(s => !s.employee);
        }
      }

      // Apply seat type filter
      if (filters.seatType !== 'all') {
        const isManagerSeat = filters.seatType === 'manager';
        seatResults = seatResults.filter(s => s.isManagerSeat === isManagerSeat);
      }

      setResults(prev => ({ ...prev, seats: seatResults }));
    } catch (error) {
    }
  };

  const handleResultClick = (item, type) => {
    onResultSelect?.(item, type);
    setIsOpen(false);
    setSearchQuery('');
  };

  const applyRecentSearch = (recent) => {
    setSearchQuery(recent.query);
    setFilters(recent.filters);
  };

  const clearSearch = () => {
    setSearchQuery('');
    setResults({ employees: [], seats: [] });
  };

  const totalResults = results.employees.length + results.seats.length;

  return (
    <div className="relative">
      <div className="relative">
        <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-muted-foreground" />
        <Input
          ref={searchRef}
          type="text"
          placeholder="Search employees, seats, or extensions..."
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          onFocus={() => setIsOpen(true)}
          className="pl-10 pr-20"
        />
        <div className="absolute right-2 top-1/2 transform -translate-y-1/2 flex items-center space-x-1">
          {searchQuery && (
            <Button variant="ghost" size="sm" onClick={clearSearch} className="h-6 w-6 p-0">
              <X className="h-3 w-3" />
            </Button>
          )}
          <Button variant="ghost" size="sm" onClick={() => setIsOpen(!isOpen)} className="h-6 w-6 p-0">
            <Filter className="h-3 w-3" />
          </Button>
        </div>
      </div>

      {isOpen && (
        <Card className="absolute top-full left-0 right-0 mt-2 z-50 glass-card max-h-96 overflow-hidden">
          <CardContent className="p-0">
            {/* Filters */}
            <div className="p-4 border-b border-border bg-muted/50">
              <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                <div>
                  <Label className="text-xs font-medium">Type</Label>
                  <select
                    value={filters.type}
                    onChange={(e) => setFilters(prev => ({ ...prev, type: e.target.value }))}
                    className="w-full text-xs border rounded px-2 py-1 mt-1"
                  >
                    <option value="all">All</option>
                    <option value="employees">Employees</option>
                    <option value="seats">Seats</option>
                  </select>
                </div>
                
                <div>
                  <Label className="text-xs font-medium">Status</Label>
                  <select
                    value={filters.status}
                    onChange={(e) => setFilters(prev => ({ ...prev, status: e.target.value }))}
                    className="w-full text-xs border rounded px-2 py-1 mt-1"
                  >
                    <option value="all">All</option>
                    <option value="assigned">Assigned</option>
                    <option value="unassigned">Unassigned</option>
                    <option value="available">Available</option>
                    <option value="occupied">Occupied</option>
                  </select>
                </div>
                
                <div>
                  <Label className="text-xs font-medium">Designation</Label>
                  <select
                    value={filters.designation}
                    onChange={(e) => setFilters(prev => ({ ...prev, designation: e.target.value }))}
                    className="w-full text-xs border rounded px-2 py-1 mt-1"
                  >
                    <option value="all">All</option>
                    <option value="manager">Manager</option>
                    <option value="regular">Regular</option>
                  </select>
                </div>
                
                <div>
                  <Label className="text-xs font-medium">Seat Type</Label>
                  <select
                    value={filters.seatType}
                    onChange={(e) => setFilters(prev => ({ ...prev, seatType: e.target.value }))}
                    className="w-full text-xs border rounded px-2 py-1 mt-1"
                  >
                    <option value="all">All</option>
                    <option value="manager">Manager</option>
                    <option value="regular">Regular</option>
                  </select>
                </div>
              </div>
            </div>

            {/* Results */}
            <div className="max-h-64 overflow-y-auto custom-scrollbar">
              {loading ? (
                <div className="p-4 text-center">
                  <div className="loading-spinner h-6 w-6 mx-auto"></div>
                </div>
              ) : totalResults === 0 && searchQuery ? (
                <div className="p-4 text-center text-muted-foreground">
                  <Search className="h-8 w-8 mx-auto mb-2 opacity-50" />
                  <p>No results found for "{searchQuery}"</p>
                </div>
              ) : (
                <div className="space-y-1">
                  {/* Employee Results */}
                  {results.employees.length > 0 && (
                    <div>
                      <div className="px-4 py-2 bg-muted/30 text-xs font-medium text-muted-foreground">
                        Employees ({results.employees.length})
                      </div>
                      {results.employees.map((employee) => (
                        <div
                          key={employee.employeeId}
                          className="px-4 py-3 hover:bg-accent cursor-pointer border-b border-border/50"
                          onClick={() => handleResultClick(employee, 'employee')}
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
                              <div className="flex items-center space-x-2 mt-1">
                                <Badge 
                                  variant={employee.designation?.isManager ? "default" : "secondary"} 
                                  className="text-xs"
                                >
                                  {employee.designation?.designation}
                                </Badge>
                                {employee.seat && (
                                  <Badge variant="outline" className="text-xs">
                                    <MapPin className="h-3 w-3 mr-1" />
                                    {employee.seat.seatNumber}
                                  </Badge>
                                )}
                              </div>
                            </div>
                          </div>
                        </div>
                      ))}
                    </div>
                  )}

                  {/* Seat Results */}
                  {results.seats.length > 0 && (
                    <div>
                      <div className="px-4 py-2 bg-muted/30 text-xs font-medium text-muted-foreground">
                        Seats ({results.seats.length})
                      </div>
                      {results.seats.map((seat) => (
                        <div
                          key={seat.seatNumber}
                          className="px-4 py-3 hover:bg-accent cursor-pointer border-b border-border/50"
                          onClick={() => handleResultClick(seat, 'seat')}
                        >
                          <div className="flex items-center space-x-3">
                            <div className="p-2 rounded-full bg-primary/10">
                              <Armchair className="h-4 w-4 text-green-600" />
                            </div>
                            <div className="flex-1 min-w-0">
                              <p className="font-medium">{seat.seatNumber}</p>
                              <p className="text-xs text-muted-foreground">Extension: {seat.extension}</p>
                              <div className="flex items-center space-x-2 mt-1">
                                <Badge 
                                  variant={seat.isManagerSeat ? "default" : "secondary"} 
                                  className="text-xs"
                                >
                                  {seat.isManagerSeat ? 'Manager Seat' : 'Regular Seat'}
                                </Badge>
                                {seat.employee ? (
                                  <Badge variant="outline" className="text-xs">
                                    <User className="h-3 w-3 mr-1" />
                                    {seat.employee.name}
                                  </Badge>
                                ) : (
                                  <Badge variant="success" className="text-xs">
                                    Available
                                  </Badge>
                                )}
                              </div>
                            </div>
                          </div>
                        </div>
                      ))}
                    </div>
                  )}

                  {/* Recent Searches */}
                  {totalResults === 0 && !searchQuery && recentSearches.length > 0 && (
                    <div>
                      <div className="px-4 py-2 bg-muted/30 text-xs font-medium text-muted-foreground">
                        Recent Searches
                      </div>
                      {recentSearches.map((recent, index) => (
                        <div
                          key={index}
                          className="px-4 py-3 hover:bg-accent cursor-pointer border-b border-border/50"
                          onClick={() => applyRecentSearch(recent)}
                        >
                          <div className="flex items-center space-x-3">
                            <Clock className="h-4 w-4 text-muted-foreground" />
                            <div className="flex-1">
                              <p className="text-sm font-medium">{recent.query}</p>
                              <p className="text-xs text-muted-foreground">
                                {new Date(recent.timestamp).toLocaleString()}
                              </p>
                            </div>
                          </div>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              )}
            </div>
          </CardContent>
        </Card>
      )}
    </div>
  );
};

export default AdvancedSearch;