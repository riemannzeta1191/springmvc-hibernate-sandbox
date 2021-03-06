package customermodule.dao;

import customermodule.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    //injecting session factory
    @Autowired
    private SessionFactory sessionFactory;


    @Override
//    @Transactional removed this after including service layer into our architecture
    public List<Customer> getCustomers() {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // create a query
        Query<Customer> theQuery =
                currentSession.createQuery("from Customer", Customer.class);

        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();


        //sorting by last name
        /*customers.sort(new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });*/
        customers.sort((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()));

        // return the results
        return customers;
    }

    @Override
    public void saveCustomer(Customer customer) {
        //get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        //save the customer to the DB OR update
        currentSession.saveOrUpdate(customer);
    }

    @Override
    public Customer getCustomer(int theId) {
        //get current hibernate session
        Session session = sessionFactory.getCurrentSession();

        //get the customer
        Customer customer = session.get(Customer.class, theId);

        //return customer
        return customer;
    }

    @Override
    public void deleteCustomer(int theId) {
        //get current session
        Session session = sessionFactory.getCurrentSession();

        //get the customer
//        Customer customer = session.get(Customer.class, theId);
//        //delete the customer
//        session.remove(customer);
        Query query = session.createQuery("delete from Customer where id =:customerId");
        query.setParameter("customerId", theId);
        query.executeUpdate();


    }
}
