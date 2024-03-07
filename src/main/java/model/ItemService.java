package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ItemService {
    private SessionFactory sessionFactory;

    public ItemService() {
        this.sessionFactory = sessionFactory;
    }

    public String reassignItemToBox(Long itemId, Long boxId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Obtener el ítem y la caja de la base de datos
            Item item = session.get(Item.class, itemId);
            Box boxe = session.get(Box.class, boxId);

            // Verificar si el ítem y la caja existen
            if (item == null) {
                return "El ítem no existe.";
            }
            if (boxe == null) {
                return "La caja no existe.";
            }

            // Asignar la caja al ítem
            item.setBox((Box) boxe);

            // Actualizar el ítem en la base de datos
            session.update(item);

            transaction.commit();
            return "Item reubicado correctamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fallo al reubicar el item.";
        }
    }

    public List<Item> listAllItems() {
        try (Session session = sessionFactory.openSession()) {
            // Obtener todos los ítems de la base de datos
            Query<Item> query = session.createQuery("FROM Item", Item.class);
            List<Item> items = query.getResultList();
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
