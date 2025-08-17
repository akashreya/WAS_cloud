import React, { useState, useEffect } from 'react';
import { Plus, Armchair, Crown, User, Trash2, ArrowRightLeft } from 'lucide-react';
import { seatApi } from '../services/api';
import toast from 'react-hot-toast';

const SeatManagement = () => {
  const [seats, setSeats] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [loading, setLoading] = useState(true);
  const [formData, setFormData] = useState({
    seatNumber: '',
    extensionNumber: '',
    isManagerSeat: false,
  });

  useEffect(() => {
    fetchSeats();
  }, []);

  const fetchSeats = async () => {
    try {
      const response = await seatApi.getAll();
      setSeats(response.data.data);
    } catch (error) {
      toast.error('Failed to fetch seats');
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await seatApi.create(formData);
      toast.success('Seat created successfully');
      setShowModal(false);
      setFormData({ seatNumber: '', extensionNumber: '', isManagerSeat: false });
      fetchSeats();
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to create seat');
    }
  };

  const handleDelete = async (seatNumber) => {
    if (window.confirm('Are you sure you want to delete this seat?')) {
      try {
        await seatApi.delete(seatNumber);
        toast.success('Seat deleted successfully');
        fetchSeats();
      } catch (error) {
        toast.error(error.response?.data?.message || 'Delete failed');
      }
    }
  };

  const handleUnassign = async (seatNumber) => {
    try {
      await seatApi.unassign(seatNumber);
      toast.success('Seat unassigned successfully');
      fetchSeats();
    } catch (error) {
      toast.error(error.response?.data?.message || 'Unassign failed');
    }
  };

  const availableSeats = (seats || []).filter(seat => !seat.employee);
  const occupiedSeats = (seats || []).filter(seat => seat.employee);
  const managerSeats = (seats || []).filter(seat => seat.isManagerSeat);

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Seat Management</h1>
          <p className="text-gray-600">Manage office seating arrangements</p>
        </div>
        <button
          onClick={() => {
            setFormData({ seatNumber: '', extensionNumber: '', isManagerSeat: false });
            setShowModal(true);
          }}
          className="btn btn-primary flex items-center space-x-2"
        >
          <Plus className="h-4 w-4" />
          <span>Add Seat</span>
        </button>
      </div>

      {/* Statistics */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        <div className="card p-6">
          <div className="flex items-center">
            <div className="p-3 rounded-lg bg-blue-100">
              <Armchair className="h-6 w-6 text-blue-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Total Seats</p>
              <p className="text-2xl font-bold text-gray-900">{(seats || []).length}</p>
            </div>
          </div>
        </div>
        
        <div className="card p-6">
          <div className="flex items-center">
            <div className="p-3 rounded-lg bg-green-100">
              <Armchair className="h-6 w-6 text-green-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Available</p>
              <p className="text-2xl font-bold text-gray-900">{availableSeats.length}</p>
            </div>
          </div>
        </div>
        
        <div className="card p-6">
          <div className="flex items-center">
            <div className="p-3 rounded-lg bg-red-100">
              <User className="h-6 w-6 text-red-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Occupied</p>
              <p className="text-2xl font-bold text-gray-900">{occupiedSeats.length}</p>
            </div>
          </div>
        </div>
        
        <div className="card p-6">
          <div className="flex items-center">
            <div className="p-3 rounded-lg bg-purple-100">
              <Crown className="h-6 w-6 text-purple-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Manager Seats</p>
              <p className="text-2xl font-bold text-gray-900">{managerSeats.length}</p>
            </div>
          </div>
        </div>
      </div>

      {/* Seat Table */}
      <div className="card overflow-hidden">
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Seat Details
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Type
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Assigned Employee
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Status
                </th>
                <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Actions
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {(seats || []).map((seat) => (
                <tr key={seat.seatNumber} className="hover:bg-gray-50">
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="flex items-center">
                      <div className="p-2 bg-gray-100 rounded-full">
                        <Armchair className="h-5 w-5 text-gray-600" />
                      </div>
                      <div className="ml-4">
                        <div className="text-sm font-medium text-gray-900">{seat.seatNumber}</div>
                        <div className="text-sm text-gray-500">Ext: {seat.extensionNumber}</div>
                      </div>
                    </div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="flex items-center">
                      {seat.isManagerSeat ? (
                        <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-purple-100 text-purple-800">
                          <Crown className="h-3 w-3 mr-1" />
                          Manager
                        </span>
                      ) : (
                        <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-gray-100 text-gray-800">
                          Regular
                        </span>
                      )}
                    </div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    {seat.employee ? (
                      <div>
                        <div className="text-sm font-medium text-gray-900">{seat.employee.name}</div>
                        <div className="text-sm text-gray-500">{seat.employee.employeeId}</div>
                      </div>
                    ) : (
                      <span className="text-sm text-gray-500 italic">Unassigned</span>
                    )}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    {seat.employee ? (
                      <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-red-100 text-red-800">
                        Occupied
                      </span>
                    ) : (
                      <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">
                        Available
                      </span>
                    )}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                    <div className="flex justify-end space-x-2">
                      {seat.employee && (
                        <button
                          onClick={() => handleUnassign(seat.seatNumber)}
                          className="text-orange-600 hover:text-orange-900"
                          title="Unassign employee"
                        >
                          <ArrowRightLeft className="h-4 w-4" />
                        </button>
                      )}
                      <button
                        onClick={() => handleDelete(seat.seatNumber)}
                        className="text-red-600 hover:text-red-900"
                        disabled={seat.employee}
                        title={seat.employee ? "Cannot delete occupied seat" : "Delete seat"}
                      >
                        <Trash2 className="h-4 w-4" />
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {/* Add Seat Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-6 w-full max-w-md">
            <h3 className="text-lg font-semibold mb-4">Add New Seat</h3>
            
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Seat Number
                </label>
                <input
                  type="text"
                  required
                  value={formData.seatNumber}
                  onChange={(e) => setFormData({ ...formData, seatNumber: e.target.value })}
                  className="input"
                  placeholder="e.g., WCP3-5F-101"
                />
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Extension Number
                </label>
                <input
                  type="text"
                  required
                  value={formData.extensionNumber}
                  onChange={(e) => setFormData({ ...formData, extensionNumber: e.target.value })}
                  className="input"
                  placeholder="e.g., 60101"
                />
              </div>
              
              <div className="flex items-center">
                <input
                  id="isManagerSeat"
                  type="checkbox"
                  checked={formData.isManagerSeat}
                  onChange={(e) => setFormData({ ...formData, isManagerSeat: e.target.checked })}
                  className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300 rounded"
                />
                <label htmlFor="isManagerSeat" className="ml-2 block text-sm text-gray-900">
                  Manager Seat
                </label>
              </div>
              
              <div className="flex justify-end space-x-3 pt-4">
                <button
                  type="button"
                  onClick={() => setShowModal(false)}
                  className="btn btn-secondary"
                >
                  Cancel
                </button>
                <button type="submit" className="btn btn-primary">
                  Create Seat
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default SeatManagement;