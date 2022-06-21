package model;

/**
 * class for subclass of Parts class, InHouse
 */
public class InHouse extends Part {

    private int machineId;

    /** Constructor for InHouse
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     *
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return the machine ID
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId the machine ID to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
