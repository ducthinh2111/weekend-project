public class AddressService {

    @Inject
    private AddressDao addrDao;

    private static final String STATIC_FIELD = "static field";

    public Address create(Address address) {
        return addrDao.create(address);
    }

    public int delete(Address address) {
        return addrDao.delete(address);
    }

    public void update(Address address) {
        addrDao.update(address);
    }

    public Address merge(Address addr1, Address addr2) {
        return addrDao.merge(addr1, addr2);
    }
}