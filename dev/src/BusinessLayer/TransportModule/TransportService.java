package src.BusinessLayer.TransportModule;

public class TransportService {
        Pool pool;
        public TransportService(){
            pool = pool.getInstance();
        }
        public boolean hasTransport(int brunchId, String date) {  return pool.hasTransport(brunchId, date); }

        public String getStringRequest(int orderId) { return pool.getReqString(orderId); }


    }


