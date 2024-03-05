package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class LoanService {
    private SessionFactory sessionFactory;

    public LoanService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public String loanItemToUser(Long item_id, Long user_id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener el ítem y el usuario de la base de datos
            Item item = session.get(Item.class, item_id);
            User user = session.get(User.class, user_id);

            // Verificar si el ítem y el usuario existen
            if (item == null) {
                return "El ítem no existe.";
            }
            if (user == null) {
                return "El usuario no existe.";
            }

            // Crear un nuevo préstamo
            Loan loan = new Loan();
            loan.setItem(item);
            loan.setUser(String.valueOf(user));
            loan.setCheckout_date(new Date().getTime());

            // Guardar el préstamo en la base de datos
            session.save(loan);

            transaction.commit();
            return "Item prestado correctamente.";
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return "Error al prestar el item: " + e.getMessage();
        } finally {
            session.close();
        }
    }

    public String returnItem(Long item_id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener el préstamo asociado al ítem
            Loan loan = session.createQuery("FROM Loan WHERE item.id = :item_id AND returnedDate IS NULL", Loan.class)
                    .setParameter("item_id", item_id)
                    .uniqueResult();

            // Verificar si se encontró un préstamo
            if (loan == null) {
                return "No se encontró un préstamo activo para este ítem.";
            }

            // Establecer la fecha de devolución y actualizar el préstamo
            loan.setReturned_date(new Date().getTime());
            session.update(loan);

            transaction.commit();
            return "Item devuelto correctamente.";
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return "Error al devolver el item: " + e.getMessage();
        } finally {
            session.close();
        }
    }

    public List<Item> getItemsLoanedToUser(Long user_id) {
        try (Session session = sessionFactory.openSession()) {
            // Crear una consulta HQL para obtener los ítems prestados al usuario dado
            String hql = "SELECT l.item FROM Loan l WHERE l.user.id = :user_id";
            org.hibernate.query.Query<Item> query = session.createQuery(hql, Item.class);
            query.setParameter("user_id", user_id);
            List<Item> items = query.getResultList();
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Item> getAllItemsOnLoan() {
        try (Session session = sessionFactory.openSession()) {
            // Crear una consulta HQL para obtener todos los ítems que están en préstamo
            String hql = "SELECT DISTINCT l.item FROM Loan l WHERE l.returnedDate IS NULL";
            org.hibernate.query.Query<Item> query = session.createQuery(hql, Item.class);
            List<Item> items = query.getResultList();
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Loan> getAllLoansFromItem(Long item_id) {
        try (Session session = sessionFactory.openSession()) {
            // Crear una consulta HQL para obtener todos los préstamos del ítem dado
            String hql = "FROM Loan l WHERE l.item.id = :item_id";
            Query<Loan> query = session.createQuery(hql, Loan.class);
            query.setParameter("item_id", item_id);
            List<Loan> loans = query.getResultList();
            return loans;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
