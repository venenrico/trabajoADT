package org.example;

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
                    String name=scanner.nextLine();
                    System.out.println("introducir descripcion del Item..");
                    String descripcion=scanner.nextLine();
                    Item item1=new Item(name,descripcion);
                    session.persist(item1);

                    session.getTransaction().commit();

                    session.close();
                    
                    break;
                case 2:
                    System.out.println("Buscando por id...");
                    // Lógica para buscar por id
                    Session session1 = HibernateUtil.getSessionFactory().openSession();

                    session1.beginTransaction();



                    var item1FromDb = session1.find(Item.class, 1L);

                    System.out.println(item1FromDb);
                    break;
                case 3:
                    System.out.println("Buscando por nombre...");
                    // Lógica para buscar por nombre
                    Session session2 = HibernateUtil.getSessionFactory().openSession();

                    session2.beginTransaction();

;
                    session2.getTransaction().commit();

                    var item2FromDb = session2.find(Item.class, 1L);

                    System.out.println(item2FromDb);
                    break;
                case 4:
                    System.out.println("Actualizando un item...");
                    Session session3 = HibernateUtil.getSessionFactory().openSession();




                    var emp1 = new Employee("employee1", 23);
                    emp1.setId(3L);
                    emp1.setAge(24);

                    session3.beginTransaction();
                    session3.merge(emp1);
                    session3.getTransaction().commit();

                    System.out.println(emp1);
                    break;
                case 5:
                    System.out.println("Borrando un item...");
                    Session session4 = HibernateUtil.getSessionFactory().openSession();
                    session4.beginTransaction();
                    session4.remove(session4.find(Item.class, 1L));
                    session4.getTransaction().commit();
                    break;
                case 6:
                    System.out.println("Asignando categoría...");
                    // Lógica para asignar categoría
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
}
