package cz.muni.fi.pv168.seminar01.delta.wiring;

import cz.muni.fi.pv168.seminar01.delta.data.storage.dao.AutoDao;
import cz.muni.fi.pv168.seminar01.delta.data.storage.dao.CategoryDao;
import cz.muni.fi.pv168.seminar01.delta.data.storage.dao.DestinationDao;
import cz.muni.fi.pv168.seminar01.delta.data.storage.dao.RideDao;
import cz.muni.fi.pv168.seminar01.delta.data.storage.db.ConnectionHandler;
import cz.muni.fi.pv168.seminar01.delta.data.storage.db.DatabaseManager;
import cz.muni.fi.pv168.seminar01.delta.data.storage.mapper.AutoMapper;
import cz.muni.fi.pv168.seminar01.delta.data.storage.mapper.CategoryMapper;
import cz.muni.fi.pv168.seminar01.delta.data.storage.mapper.DestinationMapper;
import cz.muni.fi.pv168.seminar01.delta.data.storage.mapper.RideMapper;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.AutoRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.CategoryRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.DestinationRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.RideRepository;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.Category;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;

public class DependencyProvider {
    private final ConnectionHandler connectionHandler;

    public DependencyProvider() {
        this.connectionHandler = DatabaseManager.initializeDatabase();
    }

    public Repository<Ride> getRideRepository() {
        return new RideRepository(new RideDao(connectionHandler), new RideMapper());
    }

    public Repository<Category> getCategoryRepository() {
        return new CategoryRepository(new CategoryDao(connectionHandler), new CategoryMapper());
    }

    public Repository<Auto> getAutoRepository() {
        return new AutoRepository(new AutoDao(connectionHandler), new AutoMapper());
    }

    public Repository<Destination> getDestinationRepository() {
        return new DestinationRepository(new DestinationDao(connectionHandler), new DestinationMapper());
    }

    public void closeConnection() {
        connectionHandler.close();
    }
}
