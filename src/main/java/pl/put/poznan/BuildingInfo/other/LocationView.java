package pl.put.poznan.BuildingInfo.other;

/**
 * <code>LocationView</code> class provides views for {@link com.fasterxml.jackson.annotation.JsonView} annotations.
 */
public class LocationView {
    /**
     * contains id and name of location
     */
    public interface Basic {}

    /**
     * contains id, name and area of location
     */
    public interface Area extends Basic {}

    /**
     * contains id, name and cubature of location
     */
    public interface Cube extends Basic {}

    /**
     * contains id, name and calculated average lighting power of location
     */
    public interface Lighting extends Basic {}

    /**
     * contains id, name and calculated average heating energy of location
     */
    public interface Heating extends Basic {}

    /**
     * contains all information about all locations
     */
    public interface All extends Basic {}
}
