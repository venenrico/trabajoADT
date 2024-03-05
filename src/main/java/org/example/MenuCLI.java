package org.example;

import model.Category;
import model.Item;
import org.hibernate.Session;

import java.util.Scanner;

public class MenuCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("Menú:");
            System.out.println("1. Registrar un item.");
            System.out.println("2. Buscar por id.");
            System.out.println("3. Buscar por nombre.");
            System.out.println("4. Actualizar item.");
            System.out.println("5. Borrar item.");
            System.out.println("6. Asignar categoría.");
            System.out.println("7. Salir.");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    Session session = HibernateUtil.getSessionFactory().openSession();

                    session.beginTransaction();
                    System.out.println("Registrando un item...");
                    System.out.println("introducir nombre del Item..");
                    String name = scanner.nextLine();
                    System.out.println("introducir descripcion del Item..");
                    String descripcion = scanner.nextLine();
                    Item item1 = new Item(name, descripcion);
                    session.persist(item1);

                    session.getTransaction().commit();

                    session.close();

                    break;
                case 2:
                    System.out.println("Buscando por id...");
                    // Lógica para buscar por id
                    Session session1 = HibernateUtil.getSessionFactory().openSession();


                    System.out.print("Ingrese el id del item a buscar: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();

                    Item item = session1.get(Item.class, id + "L");
                    if (item != null) {
                        System.out.println("Item encontrado:");
                        System.out.println("Nombre: " + item.getName());
                    } else {
                        System.out.println("No se ha encontrado el item.");
                    }
                    session1.close();


                    break;
                case 3:
                    System.out.println("Buscando por nombre...");
                    // Lógica para buscar por nombre
                    Session session2 = HibernateUtil.getSessionFactory().openSession();

                    session2.beginTransaction();
                    System.out.print("Ingrese el nombre del item a buscar: ");
                    String nombre = scanner.nextLine();

                    // Utilizando HQL para buscar por nombre
                    String hql = "FROM Item WHERE name = :itemName";
                    Item item2 = session2.createQuery(hql, Item.class)
                            .setParameter("itemName", nombre)
                            .uniqueResult();

                    if (item2 != null) {
                        System.out.println("Item encontrado:");
                        System.out.println("Nombre: " + item2.getName());

                    } else {
                        System.out.println("No se ha encontrado el item.");
                    }
                    ;
                    session2.close();


                    break;
                case 4:
                    updateItem();
                    break;
                case 5:
                    System.out.println("Borrando un item...");
                    Session session4 = HibernateUtil.getSessionFactory().openSession();
                    session4.beginTransaction();
                    session4.remove(session4.find(Item.class, 1L));
                    session4.getTransaction().commit();
                    session4.close();
                    break;
                case 6:
                    assignCategory();
                    break;
                case 7:
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                    break;
            }
        }
        scanner.close();
    }

    public static void updateItem() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Scanner scanner = new Scanner(System.in);
            session.beginTransaction();

            System.out.print("Ingrese el id del item a actualizar: ");
            Long id = scanner.nextLong();
            scanner.nextLine();

            Item newItem = new Item();
            newItem.setId(Math.toIntExact(id)); // Establecer el ID del item a actualizar

            Item item = session.merge(newItem); // Fusionar el item con el contexto de persistencia actual

            if (item != null) {
                System.out.println("Ingrese los nuevos datos del item (deje en blanco para mantener los datos existentes):");
                System.out.print("Nuevo nombre: ");
                String newName = scanner.nextLine();
                if (!newName.isEmpty()) {
                    item.setName(newName);
                }
                System.out.print("Nueva descripción: ");
                String newDescription = scanner.nextLine();
                if (!newDescription.isEmpty()) {
                    item.setDescription(newDescription);
                }
                session.getTransaction().commit();
                System.out.println("Item actualizado correctamente.");
            } else {
                System.out.println("No se ha encontrado el item.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar el item: " + e.getMessage());
        }
    }

    public static void assignCategory() {
        Scanner scanner = new Scanner(System.in);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            System.out.print("Ingrese el id del item al que desea asignar una categoría: ");
            long itemId = scanner.nextLong();
            scanner.nextLine(); // Consume newline

            Item item = session.get(Item.class, itemId);
            if (item != null) {
                System.out.print("Ingrese el nombre de la categoría: ");
                String categoryName = scanner.nextLine();

                Category category = session.createQuery("FROM Category WHERE name = :name", Category.class)
                        .setParameter("name", categoryName)
                        .uniqueResult();

                if (category != null) {
                    item.getCategories().add(category);
                    session.update(item);
                    session.getTransaction().commit();
                    System.out.println("Categoría asignada correctamente.");
                } else {
                    System.out.println("No se ha encontrado la categoría.");
                }
            } else {
                System.out.println("No se ha encontrado el item.");
            }
        } catch (Exception e) {
            System.out.println("Error al asignar la categoría: " + e.getMessage());
        }
    }


}
